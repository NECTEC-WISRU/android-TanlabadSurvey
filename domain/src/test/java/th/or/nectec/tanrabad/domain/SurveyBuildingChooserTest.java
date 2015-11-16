package th.or.nectec.tanrabad.domain;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import th.or.nectec.tanrabad.entity.Building;
import th.or.nectec.tanrabad.entity.Place;
import th.or.nectec.tanrabad.entity.User;

public class SurveyBuildingChooserTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private SurveyRepository surveyRepository;
    private PlaceRepository placeRepository;
    private UserRepository userRepository;
    private SurveyBuildingListPresenter surveyBuildingListPresenter;
    private Place place;
    private User user;
    private String placeUUID;
    private String username;
    private BuildingRepository buildingRepository;
    private Building building1;
    private Building building2;
    private Building building3;

    @Before
    public void setUp() {
        surveyRepository = context.mock(SurveyRepository.class);
        placeRepository = context.mock(PlaceRepository.class);
        buildingRepository = context.mock(BuildingRepository.class);
        userRepository = context.mock(UserRepository.class);
        surveyBuildingListPresenter = context.mock(SurveyBuildingListPresenter.class);

        placeUUID = UUID.nameUUIDFromBytes("1abc".getBytes()).toString();
        username = "ice";

        place = new Place(UUID.fromString(placeUUID), "ทดสอบ");
        user = User.fromUsername(username);

        building1 = Building.withName("123");
        building1.setPlace(place);

        building2 = Building.withName("124");
        building2.setPlace(place);

        building3 = Building.withName("125");
        building3.setPlace(place);
    }

    @Test
    public void testShowSurveyBuildingList() throws Exception {

        final List<Building> buildings = new ArrayList<>();
        buildings.add(building1);
        buildings.add(building2);
        buildings.add(building3);

        final List<Building> surveyBuildings = new ArrayList<>();
        surveyBuildings.add(building2);

        final List<SurveyBuilding> surveyBuildingsWithStatus = new ArrayList<>();
        surveyBuildingsWithStatus.add(new SurveyBuilding(building1, false));
        surveyBuildingsWithStatus.add(new SurveyBuilding(building2, true));
        surveyBuildingsWithStatus.add(new SurveyBuilding(building3, false));

        context.checking(new Expectations() {
            {
                allowing(placeRepository).findPlaceByPlaceUUID(with(UUID.fromString(placeUUID)));
                will(returnValue(place));

                allowing(userRepository).findUserByName(with(username));
                will(returnValue(user));

                allowing(buildingRepository).findBuildingInPlace(with(place.getId()));
                will(returnValue(buildings));

                allowing(surveyRepository).findByPlaceAndUserIn7Days(with(place), with(user));
                will(returnValue(surveyBuildings));

                allowing(surveyBuildingListPresenter).displayAllSurveyBuildingList(with(surveyBuildingsWithStatus));

            }
        });
        SurveyBuildingChooser surveyBuildingHistoryController = new SurveyBuildingChooser(userRepository, placeRepository, buildingRepository, surveyRepository, surveyBuildingListPresenter);
        surveyBuildingHistoryController.displaySurveyBuildingOf(placeUUID, username);
    }


    @Test
    public void testShowBuildingNotSurveyList() throws Exception {

        final List<Building> buildings = new ArrayList<>();
        buildings.add(building1);
        buildings.add(building2);
        buildings.add(building3);


        final List<SurveyBuilding> surveyBuildingsWithStatus = new ArrayList<>();
        surveyBuildingsWithStatus.add(new SurveyBuilding(building1, false));
        surveyBuildingsWithStatus.add(new SurveyBuilding(building2, false));
        surveyBuildingsWithStatus.add(new SurveyBuilding(building3, false));

        context.checking(new Expectations() {
            {
                allowing(placeRepository).findPlaceByPlaceUUID(with(UUID.fromString(placeUUID)));
                will(returnValue(place));

                allowing(userRepository).findUserByName(with(username));
                will(returnValue(user));

                allowing(buildingRepository).findBuildingInPlace(with(place.getId()));
                will(returnValue(buildings));

                allowing(surveyRepository).findByPlaceAndUserIn7Days(with(place), with(user));
                will(returnValue(null));

                allowing(surveyBuildingListPresenter).displayAllSurveyBuildingList(with(surveyBuildingsWithStatus));

            }
        });
        SurveyBuildingChooser surveyBuildingHistoryController = new SurveyBuildingChooser(userRepository, placeRepository, buildingRepository, surveyRepository, surveyBuildingListPresenter);
        surveyBuildingHistoryController.displaySurveyBuildingOf(placeUUID, username);
    }

    @Test
    public void testAlertNoBuildingFound() throws Exception {
        context.checking(new Expectations() {
            {
                allowing(userRepository).findUserByName(with(username));
                will(returnValue(user));

                allowing(placeRepository).findPlaceByPlaceUUID(with(UUID.fromString(placeUUID)));
                will(returnValue(place));

                allowing(buildingRepository).findBuildingInPlace(with(place.getId()));
                will(returnValue(null));

                never(surveyRepository);

                allowing(surveyBuildingListPresenter).alertBuildingsNotFound();

            }
        });
        SurveyBuildingChooser surveyBuildingHistoryController = new SurveyBuildingChooser(userRepository, placeRepository, buildingRepository, surveyRepository, surveyBuildingListPresenter);
        surveyBuildingHistoryController.displaySurveyBuildingOf(placeUUID, username);

    }

    @Test
    public void testNotFoundUser() throws Exception {

        context.checking(new Expectations() {
            {
                allowing(userRepository).findUserByName(with(username));
                will(returnValue(null));
                oneOf(surveyBuildingListPresenter).alertUserNotFound();
            }
        });
        SurveyBuildingChooser surveyBuildingChooser = new SurveyBuildingChooser(userRepository, placeRepository, buildingRepository, surveyRepository, surveyBuildingListPresenter);
        surveyBuildingChooser.displaySurveyBuildingOf(placeUUID, username);
    }

    @Test
    public void testNotFoundPlace() throws Exception {

        context.checking(new Expectations() {
            {
                allowing(userRepository).findUserByName(with(username));
                will(returnValue(user));

                allowing(placeRepository).findPlaceByPlaceUUID(with(UUID.fromString(placeUUID)));
                will(returnValue(null));
                oneOf(surveyBuildingListPresenter).alertPlaceNotFound();
            }
        });
        SurveyBuildingChooser surveyBuildingChooser = new SurveyBuildingChooser(userRepository, placeRepository, buildingRepository, surveyRepository, surveyBuildingListPresenter);
        surveyBuildingChooser.displaySurveyBuildingOf(placeUUID, username);
    }
}



