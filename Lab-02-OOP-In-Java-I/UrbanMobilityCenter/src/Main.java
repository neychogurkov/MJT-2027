import bg.sofia.uni.fmi.mjt.parking.ParkingClientAPI;
import bg.sofia.uni.fmi.mjt.parking.ParkingInspectorAPI;
import bg.sofia.uni.fmi.mjt.parking.UrbanMobilityCenter;
import bg.sofia.uni.fmi.mjt.parking.location.Location;
import bg.sofia.uni.fmi.mjt.parking.sanction.Sanction;
import bg.sofia.uni.fmi.mjt.parking.vehicle.Car;
import bg.sofia.uni.fmi.mjt.parking.vehicle.Jeep;
import bg.sofia.uni.fmi.mjt.parking.vehicle.LicensePlate;
import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.BlueZone;
import bg.sofia.uni.fmi.mjt.parking.zone.GreenZone;

import java.time.LocalDateTime;

void main() {

    // 1. Създаване на зони
    Location center = new Location(0, 0);

    BlueZone blueZone = new BlueZone("Blue", center, 100);
    GreenZone greenZone = new GreenZone("Green", center, 100, 200);

    UrbanMobilityCenter umc = new UrbanMobilityCenter(blueZone, greenZone);

    ParkingClientAPI client = umc;
    ParkingInspectorAPI inspector = umc;

    // 2. Създаване на превозни средства
    LicensePlate carPlate = LicensePlate.parse("СА 1234 ВХ");
    LicensePlate jeepPlate = LicensePlate.parse("CB5678TX");

    Vehicle car = new Car(carPlate);
    Vehicle jeep = new Jeep(jeepPlate);

    // 3. Проверка на LicensePlate
    System.out.println("=== LICENSE PLATE TESTS ===");

    System.out.println(LicensePlate.parse("CA1234BX") != null);
    System.out.println(LicensePlate.parse("СА1234ВХ") != null);
    System.out.println(LicensePlate.parse("ABC1234DE") == null);
    System.out.println(LicensePlate.parse(null) == null);

    // 4. Тестове за SMS плащания
    System.out.println("\n=== SMS TESTS ===");

    LocalDateTime firstPayment = LocalDateTime.of(2026, 10, 31, 10, 0);
    LocalDateTime secondPayment = LocalDateTime.of(2026, 10, 31, 10, 1);

    System.out.println(client.sendSms("1302", "CA1234BX", firstPayment));
    System.out.println(client.sendSms("1302", "СА-1234-ВХ", secondPayment));
    System.out.println(client.sendSms("1303", "CB5678TX", firstPayment));

    System.out.println(client.sendSms("9999", "CA1234BX", firstPayment));
    System.out.println(client.sendSms("1302", "INVALID", firstPayment));

    // 5. Инспекция в синя зона
    System.out.println("\n=== BLUE ZONE TESTS ===");

    Location blueLocation = new Location(50, 0);

    Sanction sanction1 = inspector.inspect(
            car, blueLocation, LocalDateTime.of(2026, 10, 31, 10, 30));

    Sanction sanction2 = inspector.inspect(
            jeep, blueLocation, LocalDateTime.of(2026, 10, 31, 10, 30));

    System.out.println("Car sanction: " + sanction1);
    System.out.println("Jeep sanction: " + sanction2);

    // 6. Проверка на изтичане на плащането
    System.out.println("\n=== PAYMENT EXPIRATION TESTS ===");

    Sanction sanction3 = inspector.inspect(
            car, blueLocation, LocalDateTime.of(2026, 10, 31, 11, 0));

    System.out.println("At 11:00: " + sanction3);

    // 7. Инспекция в зелена зона
    System.out.println("\n=== GREEN ZONE TESTS ===");

    Location greenLocation = new Location(150, 0);

    Sanction sanction4 = inspector.inspect(
            jeep, greenLocation, LocalDateTime.of(2026, 10, 31, 10, 30));

    System.out.println("Jeep sanction in green zone: " + sanction4);

    // 8. Инспекция извън зоните
    System.out.println("\n=== OUTSIDE ZONES TEST ===");

    Location outsideLocation = new Location(300, 0);

    Sanction sanction5 = inspector.inspect(
            car, outsideLocation, LocalDateTime.of(2026, 10, 31, 10, 30));

    System.out.println("Outside zone: " + sanction5);

    // 9. Проверка на сумите на санкциите
    System.out.println("\n=== SANCTION AMOUNTS ===");

    if (sanction2 != null) {
        System.out.println("Jeep tow away: " + sanction2.getAmount() + " EUR");
    }

    if (sanction4 != null) {
        System.out.println("Jeep wheel clamp: " + sanction4.getAmount() + " EUR");
    }
}