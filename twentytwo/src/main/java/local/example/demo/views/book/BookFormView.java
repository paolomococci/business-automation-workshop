package local.example.demo.views.book;

import javax.annotation.security.RolesAllowed;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import local.example.demo.views.MainLayout;

@PageTitle("Book Form")
@Route(value = "book-form", layout = MainLayout.class)
@RolesAllowed("user")
public class BookFormView {
    
}
