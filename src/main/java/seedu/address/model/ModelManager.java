package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final MeetUpList meetUpList;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<MeetUp> filteredMeetUps;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyBuyerList addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyMeetUpList meetUpList) {
        requireAllNonNull(addressBook, userPrefs, meetUpList);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs
                + "and meet up list " + meetUpList);

        logger.info("initial meet up list contains " + meetUpList);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.meetUpList = new MeetUpList(meetUpList);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredMeetUps = new FilteredList<>(this.meetUpList.getMeetUpList());

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new MeetUpList());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredMeetUps.equals(otherModelManager.filteredMeetUps);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    public Path getMeetUpListFilePath() {
        return userPrefs.getMeetUpListFilePath();
    }

    public void setMeetUpListFilePath(Path meetUpListFilePath) {
        requireNonNull(meetUpListFilePath);
        userPrefs.setMeetUpListFilePath(meetUpListFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyBuyerList addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyBuyerList getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== MeetUp List ================================================================================

    public void setMeetUpList(ReadOnlyMeetUpList meetUpList) {
        this.meetUpList.resetData(meetUpList);
    }

    public ReadOnlyMeetUpList getMeetUpList() {
        return meetUpList;
    }

    @Override
    public boolean hasMeetUp(MeetUp meetUp) {
        requireNonNull(meetUp);
        return meetUpList.hasMeetUp(meetUp);
    }
    @Override
    public void deleteMeetUp(MeetUp target) {
        meetUpList.removeMeetUp(target);
    }

    @Override
    public void addMeetUp(MeetUp meetUp) {
        meetUpList.addMeetUp(meetUp);
        updateFilteredMeetUpList(PREDICATE_SHOW_ALL_MEETUPS);
    }

    @Override
    public void setMeetUp(MeetUp target, MeetUp editedMeetUp) {
        requireAllNonNull(target, editedMeetUp);

        meetUpList.setMeetUp(target, editedMeetUp);
    }

    //=========== Filtered MeetUp List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<MeetUp> getFilteredMeetUpList() {
        return filteredMeetUps;
    }

    @Override
    public void updateFilteredMeetUpList(Predicate <MeetUp> predicate) {
        requireNonNull(predicate);
        filteredMeetUps.setPredicate(predicate);
    }

}
