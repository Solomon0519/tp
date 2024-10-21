package seedu.address.ui.property;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.property.Property;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of buyers.
 */
public class PropertyListPanel extends UiPart<Region> {
    private static final String FXML = "PropertyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PropertyListPanel.class);

    @FXML
    private ListView<Property> propertyListView;

    /**
     * Creates a {@code BuyerListPanel} with the given {@code ObservableList}.
     */
    public PropertyListPanel(ObservableList<Property> propertyList) {
        super(FXML);
        logger.info(propertyList.toString());
        propertyListView.setItems(propertyList);
        propertyListView.setCellFactory(listView -> new propertyListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Buyer} using a {@code BuyerCard}.
     */
    class propertyListViewCell extends ListCell<Property> {
        @Override
        protected void updateItem(Property property, boolean empty) {
            super.updateItem(property, empty);

            if (empty || property == null) {
                logger.info("graphic or text is null");
                setGraphic(null);
                setText(null);
            } else {
                logger.info("making new card");
                setGraphic(new PropertyCard(property, getIndex() + 1).getRoot());
            }
        }
    }

}
