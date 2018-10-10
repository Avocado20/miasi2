public class Application {

    private static AirCraftSystem airCraftSystem;

    public static void main(String[] args) {

        System.out.println("Initializing system");
        airCraftSystem = new AirCraftSystem();
        airCraftSystem.runAll();
    }

}
