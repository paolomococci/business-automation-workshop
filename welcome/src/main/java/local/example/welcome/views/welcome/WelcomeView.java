package local.example.welcome.views.welcome;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import local.example.welcome.views.MainLayout;

@PageTitle("Welcome")
@Route(value = "welcome", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class WelcomeView extends HorizontalLayout {

    private TextField nameTextField;
    private Button welcomeButton;

    public WelcomeView() {
        nameTextField = new TextField("Your name, please.");
        welcomeButton = new Button("Welcome");
        welcomeButton.addClickListener(e -> {
            Notification.show("Hello " + nameTextField.getValue());
        });

        welcomeButton.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(
                Alignment.END,
                nameTextField,
                welcomeButton);

        add(
                nameTextField,
                welcomeButton);
    }
}
