package local.mocaccino.planning;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = "anti-hiccup", variant = Lumo.DARK)
@PWA(name = "anti-hiccup", shortName = "anti-hiccup", offlineResources = { "images/logo.png" })
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class AppConfig implements AppShellConfigurator {
}
