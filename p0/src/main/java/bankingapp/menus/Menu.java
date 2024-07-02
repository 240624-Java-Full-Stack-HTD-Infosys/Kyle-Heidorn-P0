package bankingapp.menus;

public interface Menu {

    String name = "DEFAULT"; //shadow this with your menus name

    void render();
    String getName();
}
