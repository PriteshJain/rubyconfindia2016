package sh.pritesh.rubyconfindia.confsched.dao;

import com.github.gfx.android.orma.TransactionTask;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import sh.pritesh.rubyconfindia.confsched.model.Category;
import sh.pritesh.rubyconfindia.confsched.model.Category_Relation;
import sh.pritesh.rubyconfindia.confsched.model.OrmaDatabase;
import sh.pritesh.rubyconfindia.confsched.model.Place;
import sh.pritesh.rubyconfindia.confsched.model.Place_Relation;
import sh.pritesh.rubyconfindia.confsched.model.Session;
import sh.pritesh.rubyconfindia.confsched.model.Session_Relation;
import sh.pritesh.rubyconfindia.confsched.model.Speaker;
import sh.pritesh.rubyconfindia.confsched.model.Speaker_Relation;
import rx.Observable;

@Singleton
public class SessionDao {

    OrmaDatabase orma;

    @Inject
    public SessionDao(OrmaDatabase orma) {
        this.orma = orma;
    }

    public Session_Relation sessionRelation() {
        return orma.relationOfSession();
    }

    private Speaker_Relation speakerRelation() {
        return orma.relationOfSpeaker();
    }

    private Place_Relation placeRelation() {
        return orma.relationOfPlace();
    }

    private Category_Relation categoryRelation() {
        return orma.relationOfCategory();
    }

    private void insertSpeaker(Speaker speaker) {
        if (speaker != null && speakerRelation().selector().idEq(speaker.id).isEmpty()) {
            speakerRelation().inserter().execute(speaker);
        }
    }

    private void insertPlace(Place place) {
        if (place != null && placeRelation().selector().idEq(place.id).isEmpty()) {
            placeRelation().inserter().execute(place);
        }
    }

    private void insertCategory(Category category) {
        if (category != null && categoryRelation().selector().idEq(category.id).isEmpty()) {
            categoryRelation().inserter().execute(category);
        }
    }

    public Observable<List<Session>> findAll() {
        return sessionRelation().selector().executeAsObservable()
                .toList();
    }

    public Observable<List<Session>> findByChecked() {
        return sessionRelation().selector().checkedEq(true).executeAsObservable()
                .toList();
    }

    public Observable<List<Session>> findByPlace(int placeId) {
        return sessionRelation().selector().placeEq(placeId).executeAsObservable()
                .toList();
    }

    public Observable<List<Session>> findByCategory(int categoryId) {
        return sessionRelation().selector().categoryEq(categoryId).executeAsObservable()
                .toList();
    }

    public void deleteAll() {
        sessionRelation().deleter().execute();
        speakerRelation().deleter().execute();
        categoryRelation().deleter().execute();
        placeRelation().deleter().execute();
    }

    public void updateAllSync(List<Session> sessions) {
        speakerRelation().deleter().execute();
        categoryRelation().deleter().execute();
        placeRelation().deleter().execute();

        for (Session session : sessions) {
            insertSpeaker(session.speaker);
            insertCategory(session.category);
            insertPlace(session.place);
            sessionRelation().upserter().execute(session);
        }
    }


    public void updateAllAsync(List<Session> sessions) {
        orma.transactionAsync(new TransactionTask() {
            @Override
            public void execute() throws Exception {
                updateAllSync(sessions);
            }
        });
    }

    public void updateChecked(Session session) {
        sessionRelation().updater()
                .idEq(session.id)
                .checked(session.checked)
                .execute();
    }

}
