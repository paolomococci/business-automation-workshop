package local.example.demo.views.kpi;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

import local.example.demo.views.MainLayout;

@PageTitle("KPI")
@Route(value = "kpi", layout = MainLayout.class)
@PermitAll
public class KeyPerformanceIndicatorView extends VerticalLayout {

    public KeyPerformanceIndicatorView() {
        addClassName("kpi-view");
        setSpacing(false);

        Tabs kpiTabs = new Tabs(
            new Tab("#trade"), 
            new Tab("#planning"), 
            new Tab("#reception"), 
            new Tab("#filling"), 
            new Tab("#packaging"),
            new Tab("#shipment"), 
            new Tab("#quality")
        );
        kpiTabs.setWidthFull();
        setSizeFull();

        add(kpiTabs);
    }
}
