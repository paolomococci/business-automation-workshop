package local.example.demo.views.home;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import local.example.demo.views.MainLayout;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public HomeView() {
        setSpacing(false);

        Span nameSpan = new Span("admin: Jenny Doe");
        Span emailSpan = new Span("jennydoe@example.local");

        VerticalLayout contactInformation = new VerticalLayout(
            nameSpan, 
            emailSpan
        );
        contactInformation.setSpacing(false);
        contactInformation.setPadding(false);

        Details contactDetails = new Details(
            "Contact Information", 
            contactInformation
        );
        contactDetails.setOpened(false);

        add(contactDetails);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set(
            "text-align", 
            "center"
        );
    }

}
