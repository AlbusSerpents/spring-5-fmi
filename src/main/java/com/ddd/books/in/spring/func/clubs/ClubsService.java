package com.ddd.books.in.spring.func.clubs;

import com.ddd.books.in.spring.func.clubs.Club.MemberInfo;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.*;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.http.HttpStatus.*;

@Service
public class ClubsService {

    private final ClubsRepository repository;

    public ClubsService(final ClubsRepository repository) {
        this.repository = repository;
    }

    public Club create(
            final CreateClubRequest request,
            final UUID userId,
            final String name) {
        final String clubName = request.getName();
        if (repository.findByName(clubName).isPresent()) {
            throw new FunctionalException(
                    CLUB_NAME_ALREADY_TAKEN,
                    "Club with this name already exists",
                    CONFLICT);
        } else {
            final MemberInfo owner = new MemberInfo(userId, name);

            final Club club = new Club(
                    randomUUID(),
                    clubName,
                    request.getDescription(),
                    owner,
                    singletonList(owner));
            return repository.save(club);
        }
    }

    public List<ClubInfo> readAll() {
        return repository.findAllInfo();
    }

    public void join(
            final UUID clubId,
            final UUID userId,
            final String name) {
        final Club club = readById(clubId);

        if (isMember(club, userId)) {
            throw new FunctionalException(
                    MEMBER_ALREADY_EXISTS,
                    "User is already a member of this club");
        } else {
            club.getMembers().add(new MemberInfo(userId, name));
            repository.save(club);
        }
    }

    public Club readById(final UUID clubId) {
        return repository
                .findById(clubId)
                .orElseThrow(() -> new FunctionalException(MISSING, "Club doesn't exist", NOT_FOUND));
    }

    public void leave(
            final UUID clubId,
            final UUID userId,
            final String name) {
        final Club club = readById(clubId);

        if (!isMember(club, userId)) {
            throw new FunctionalException(
                    USER_NOT_A_MEMBER_OF_THE_CLUB,
                    "User is not a member of this club");
        } else if (isOwner(club, userId)) {
            throw new FunctionalException(
                    OWNER_CANNOT_LEAVE_CLUB,
                    "Owner cannot leave their own club",
                    CONFLICT);
        } else {
            club.getMembers().remove(new MemberInfo(userId, name));
            repository.save(club);
        }
    }

    public void delete(
            final UUID clubId,
            final UUID userId) {
        final Club club = readById(clubId);

        if (isOwner(club, userId)) {
            delete(clubId);
        } else {
            throw new FunctionalException(OPERATION_FORBIDDEN, null, FORBIDDEN);
        }
    }

    public void delete(final UUID clubId) {
        final boolean success = repository.deleteById(clubId);

        if (!success) {
            throw new FunctionalException(OPERATION_FAILED, null, INTERNAL_SERVER_ERROR);
        }
    }

    public Club update(final UUID clubId, final UUID userId, final UpdateClubRequest request) {
        final Club club = readById(clubId);

        if (isOwner(club, userId)) {
            Club newClub = club.withDescription(request.getDescription());
            return repository.save(newClub);
        } else {
            throw new FunctionalException(OPERATION_FORBIDDEN, null, FORBIDDEN);
        }
    }

    private boolean isOwner(final Club club, final UUID userId) {
        return club
                .getOwner()
                .getId()
                .equals(userId);
    }

    private boolean isMember(final Club club, final UUID userId) {
        return club
                .getMembers()
                .stream()
                .map(MemberInfo::getId)
                .anyMatch(userId::equals);
    }

    public List<ClubInfo> readMyClubs(final UUID userId) {
        return repository.findMyClubs(userId);
    }
}
