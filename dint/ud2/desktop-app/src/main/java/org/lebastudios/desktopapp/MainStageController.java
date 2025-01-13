package org.lebastudios.desktopapp;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.lebastudios.desktopapp.controllers.StageController;
import org.lebastudios.desktopapp.ui.MultipleItemsListView;
import org.lebastudios.desktopapp.ui.StageBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainStageController extends StageController<MainStageController>
{
    @FXML private MultipleItemsListView<String> multipleItemsList;

    @Override
    protected void initialize()
    {
        multipleItemsList.setContentGenerator(new MultipleItemsListView.ContentGenerator<>()
        {
            @Override
            public List<String> generateContent(long from, long to)
            {
                List<String> strings = new ArrayList<>();

                for (long i = from + 1; i <= to; i++)
                {
                    strings.add("" + i);
                }

                return strings;
            }

            @Override
            public long count()
            {
                return Integer.MAX_VALUE - 500;
            }
        });
        
        multipleItemsList.getListView().setCellFactory(new Callback<>()
        {
            @Override
            public ListCell<String> call(ListView<String> param)
            {
                return new ListCell<>()
                {
                    {
                        System.out.println("Builded");
                    }
                    
                    @Override
                    protected void updateItem(String item, boolean empty)
                    {
                        super.updateItem(item, empty);

                        if (item == null || item.isBlank() || empty)
                        {
                            setText(null);
                            setGraphic(null);
                            return;
                        }

                        setText(item);
                        System.out.println("a");
                    }
                };
            }
        });
    }

    @Override
    public Class<?> getBundleClass()
    {
        return Launcher.class;
    }

    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setResizeable(true);
    }

    @Override
    public boolean hasFXMLControllerDefined()
    {
        return true;
    }

    @Override
    public String getTitle()
    {
        return "Desktop App";
    }

    public void showNotification(String message, Action action)
    {
        Notifications.create()
                .text(message)
                .owner(getRoot())
                .action(action)
                .show();
    }
}