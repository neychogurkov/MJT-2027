import bg.sofia.uni.fmi.mjt.laundry.LaundryRoom;
import bg.sofia.uni.fmi.mjt.laundry.LaundryRoomAPI;
import bg.sofia.uni.fmi.mjt.laundry.basket.LaundryBasket;
import bg.sofia.uni.fmi.mjt.laundry.garment.Cotton;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;
import bg.sofia.uni.fmi.mjt.laundry.garment.Sweater;
import bg.sofia.uni.fmi.mjt.laundry.garment.TShirt;
import bg.sofia.uni.fmi.mjt.laundry.garment.Towel;
import bg.sofia.uni.fmi.mjt.laundry.program.CottonProgram;
import bg.sofia.uni.fmi.mjt.laundry.program.GentleProgram;

void main() {
    LaundryRoomAPI block60 = new LaundryRoom();

    LaundryBasket<Sweater> mariasSweaters = new LaundryBasket<>("Мария", 3.0);
    Sweater sweater = new Sweater("Reserved", 0.75);
    mariasSweaters.add(sweater);

    LaundryBasket<TShirt> ivansShirts = new LaundryBasket<>("Иван", 3.0);
    ivansShirts.addAll(List.of(new TShirt("Nike", 0.25), new TShirt("Adidas", 0.25)));

    LaundryBasket<Cotton> room412 = new LaundryBasket<>("стая 412", 2.0);
    ivansShirts.moveAllTo(room412); // 2; ivansShirts вече е празен
    room412.add(new Towel("IKEA", 1.0));

    block60.scheduleWash(mariasSweaters, new GentleProgram()); // програмата приема всякакви дрехи
    block60.scheduleWash(room412, new CottonProgram());       // в общия кош има само памук
// block60.scheduleWash(mariasSweaters, new CottonProgram()); // не се компилира: пуловерите не са памук

    block60.getScheduledWashes().size(); // 2
    block60.runNext();
    sweater.isClean(); // true
    block60.runNext();

    block60.getTokensSpent("стая 412"); // 1
    block60.getTokensSpent("Иван");     // 0: плаща собственикът на общия кош

    LaundryBasket<Garment> mixed = new LaundryBasket<>("стая 413", 5.0);
    mixed.add(new TShirt("Nike", 0.25));
// block60.scheduleWash(mixed, new CottonProgram()); // не се компилира, въпреки че в коша има само памук
    block60.scheduleWash(mixed, new GentleProgram());
    mixed.add(new Sweater("Reserved", 0.75)); // разрешено и докато кошът чака на опашката
    block60.runNext();

    room412.takeTop();     // вади изпраната хавлия, която е най-отгоре
    room412.takeTop();     // вади Adidas (редът от ivansShirts се запазва: тениската Adidas е над тази на Nike)
    room412.getWeightKg(); // 0.25
}