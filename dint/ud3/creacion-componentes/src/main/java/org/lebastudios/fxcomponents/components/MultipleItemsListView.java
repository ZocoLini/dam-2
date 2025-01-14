package org.lebastudios.fxcomponents.components;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MultipleItemsListView<T> extends VBox
{
    @Getter private final ListView<T> listView;
    private final Label actualItemsLabel;
    private final IconButton doubleLeft;
    private final IconButton left;
    private final IconButton right;
    private final IconButton doubleRight;

    private int actualGroup = 0;
    private int maxGroup;
    private Long qty;

    private ContentGenerator<T> contentGenerator;
    @Setter private ListCellNodeRecicler<T> listCellNodeRecicler;
    private int groupSize;

    public MultipleItemsListView(ListCellNodeRecicler<T> listCellNodeRecicler, ContentGenerator<T> contentGenerator,
            int groupSize)
    {
        this.listCellNodeRecicler = listCellNodeRecicler;
        this.contentGenerator = contentGenerator;
        this.groupSize = groupSize;
        
        this.listView = new ListView<>();
        listView.setCache(true);
        listView.setCacheHint(CacheHint.SPEED);

        VBox.setVgrow(listView, Priority.ALWAYS);

        this.doubleLeft = new IconButton("double-left.png");
        doubleLeft.setOnAction(_ -> showContent(0));
        this.left = new IconButton("left.png");
        left.setOnAction(_ -> showContent(actualGroup - 1));
        this.right = new IconButton("right.png");
        right.setOnAction(_ -> showContent(actualGroup + 1));
        this.doubleRight = new IconButton("double-right.png");
        doubleRight.setOnAction(_ -> showContent(maxGroup));

        this.doubleLeft.setIconSize(26);
        this.left.setIconSize(26);
        this.right.setIconSize(26);
        this.doubleRight.setIconSize(26);

        this.actualItemsLabel = new Label();

        actualItemsLabel.setAlignment(Pos.CENTER);
        actualItemsLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(actualItemsLabel, Priority.ALWAYS);

        HBox footer = new HBox(5, doubleLeft, left, actualItemsLabel, right, doubleRight);
        footer.setPadding(new Insets(5));
        footer.setSpacing(5);
        footer.setAlignment(Pos.CENTER);

        this.getChildren().addAll(listView, footer);
        
        listView.setCellFactory(new Callback<>()
        {
            @Override
            public ListCell<T> call(ListView<T> tListView)
            {
                return new ListCell<>()
                {
                    @Override
                    protected void updateItem(T item, boolean empty)
                    {
                        super.updateItem(item, empty);

                        if (empty || item == null)
                        {
                            setGraphic(null);
                            setText(null);
                            return;
                        }

                        ListCellContent content = MultipleItemsListView.this.listCellNodeRecicler.updateView(getGraphic(), item);

                        setGraphic(content.node);
                        setText(content.text);
                    }
                };
            }
        });
        
        refresh();
    }

    public MultipleItemsListView(ListCellNodeRecicler<T> listCellNodeRecicler, ContentGenerator<T> contentGenerator)
    {
        this(listCellNodeRecicler, contentGenerator, 500);
    }

    public MultipleItemsListView(ContentGenerator<T> contentGenerator)
    {
        this(new ListCellNodeRecicler<>() {}, contentGenerator);
    }
    
    public MultipleItemsListView()
    {
        this(new ContentGenerator<T>() {
            @Override
            public List<T> generateContent(long from, long to)
            {
                return List.of();
            }

            @Override
            public long count()
            {
                return 0;
            }
        });
    }
    
    public void setContentGenerator(ContentGenerator<T> contentGenerator)
    {
        this.contentGenerator = contentGenerator;
        refresh();
    }

    public void setGroupSize(int groupSize)
    {
        this.groupSize = groupSize;
        refresh();
    }

    public void refresh()
    {
        refreshListView();
    }

    private void refreshListView()
    {
        this.qty = contentGenerator.count();
        this.maxGroup = (int) (qty / groupSize);
        showContent(0);
    }

    private void showContent(int group)
    {
        if (group < 0)
        {
            throw new IllegalArgumentException("Group cannot be negative");
        }

        actualGroup = group;

        if (actualGroup == 0)
        {
            doubleLeft.setDisable(true);
            left.setDisable(true);

            right.setDisable(maxGroup == 0);
            doubleRight.setDisable(maxGroup == 0);
        }
        else
        {
            if (actualGroup == maxGroup)
            {
                doubleLeft.setDisable(false);
                left.setDisable(false);
                right.setDisable(true);
                doubleRight.setDisable(true);
            }
            else
            {
                doubleLeft.setDisable(false);
                left.setDisable(false);
                right.setDisable(false);
                doubleRight.setDisable(false);
            }
        }

        long from = (long) group * groupSize;
        long to = Math.min(from + groupSize, qty);

        listView.setItems(new ObservableListWrapper<>(contentGenerator.generateContent(from, to)));

        final var toValue = Math.min(to, qty);
        final var fromValue = Math.min(from + 1, toValue);
        actualItemsLabel.setText(String.format("%d - %d", fromValue, toValue));
    }
    
    public interface ContentGenerator<T>
    {
        List<T> generateContent(long from, long to);

        long count();
    }
    
    public interface ListCellNodeRecicler<T>
    {
        default ListCellContent updateView(Node oldNode, T item) { return new ListCellContent(oldNode, item.toString()); }
    }
    
    public record ListCellContent(Node node, String text){}
}
