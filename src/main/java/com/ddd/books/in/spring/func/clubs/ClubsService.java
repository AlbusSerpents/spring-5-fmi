package com.ddd.books.in.spring.func.clubs;

import com.ddd.books.in.spring.func.clubs.Club.MemberInfo;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.CLUB_NAME_ALREADY_TAKEN;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.CONFLICT;

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

    public List<ClubInfo> getAll() {
        return repository.findAllInfo();
    }
}
