import java.util.*;

public class Main {
    public static void main(String[] args){
        //Liczba procesów podana przez użytkownika
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj liczbę procesów: ");
        int liczba = scan.nextInt();

        List<Proces> procesy = new ArrayList<>(); //Lista wszystkich procesów
        List<Integer> kolejnosc = new ArrayList<>(); //Lista do ustalania kolejności procesów
        Queue<Proces> kolejka = new LinkedList<>(); //Lista do tworzenia kolejki procesów
        //Nadanie procesom nazwy, czasu nadejścia i długości wykonania
        for (int i = 1; i <= liczba; i++) {
            //Nadanie unikalnego czasu nadejścia
            int czas_nadejscia;
            do {
                czas_nadejscia = new Random().nextInt(100);
            } while (kolejnosc.contains(czas_nadejscia));
            kolejnosc.add(czas_nadejscia);

            //Nadanie losowej długości wykonania z przedziału od 1 do 10
            int okres = new Random().nextInt(10) + 1;
            //Nadanie Nazwy oraz atrybutów procesom
            Proces proces = new Proces("P" + i, czas_nadejscia, okres);
            procesy.add(proces);
        }

        //Wypisanie wszystkich procesów
        System.out.println("\nWszystkie procesy:");
        for (Proces proces : procesy) {
            System.out.println(proces);
        }
        System.out.println("\nFirst Come First Serve:\n");

        int okres_procesu = 0; //Dugość trwania okresu
        String nazwa = ""; //Nazwa Procesu
        int licznik=0; //Łączny czas wykonywania wszystkich procesów
        int czas = 0; //Czas który minął w trakcie czekania i wykonywania procesów
        int ilosc_procesow = 0; //Ilość wykonanych procesów
        int poczatek_procesu;
        int koniec_procesu;
        int czas_przyjscia = 0;
        boolean czy_proces = false;

        //Pętla działająca dopóki wszystkie procesy zostaną zakończone
        while (ilosc_procesow < liczba) {
            // Sprawdzenie, czy kolejka jest pusta
            if (!kolejka.isEmpty()) {
                while (!kolejka.isEmpty()) {
                    //Pobranie procesu z kolejki
                    Proces pierwszy = kolejka.poll();
                    okres_procesu = pierwszy.getOkres();
                    czas_przyjscia = pierwszy.getCzas_nadejscia();
                    nazwa = pierwszy.getNazwa();
                    //System.out.println("Kolejka nie jest pusta!");
                    poczatek_procesu = czas;
                    koniec_procesu = czas + okres_procesu-1;
                    //Pętla wyświetlająca Nazwę procesu, jego czas przyjścia, jego okres jego trwania i czas całego programu
                    for (int j = 1; j <= okres_procesu; j++) {
                        System.out.println("Proces " + nazwa + " [" + czas_przyjscia + ", " + okres_procesu + "] = " + czas + "s");
                        czas++;
                    }
                    //Wyświetlanie czasu rozpoczęcia procesu i zakończenia po jego wykonaniu
                    System.out.println("Początek procesu " + nazwa + ": " + poczatek_procesu + "s Koniec procesu: " + koniec_procesu + "s\n");
                    licznik+=okres_procesu;
                    okres_procesu = 0;
                    ilosc_procesow++;
                    //System.out.println("Ilosć procesów zakończonych: " + ilosc_procesow);
                    kolejka.remove(pierwszy); //usuwanie procesu z kolejki

                    //Sprawdzanie czy w trakcie wykonywania procesu został dodany nowy proces do wykonania
                    for (int i = poczatek_procesu; i <= koniec_procesu; i++) {
                        for (Proces proces : procesy) {
                            if (proces.getCzas_nadejscia() == i && !proces.getNazwa().equals(nazwa)) {
                                kolejka.add(proces);
                                //Dodawanie do kolejki wypisywane na ekran do sprawdzania poprawnego działania programu
                                System.out.println("Dodawanie do kolejki procesu: " + proces.getNazwa());
                            }
                        }
                    }
                }
            }
            //Sprawdzanie czy w danym czasie został zlecony jakiś proces
            for (Proces proces : procesy) {
                if (proces.getCzas_nadejscia() == czas) {
                    //Zapisywanie atrybutów procesu
                    czy_proces = true;
                    czas_przyjscia = proces.getCzas_nadejscia();
                    okres_procesu += proces.getOkres();
                    nazwa = proces.getNazwa();
                    break;
                }
            }
            //Wykonywanie procesu w przypadku gdy został on zlecony
            if (czy_proces) {
                poczatek_procesu = czas;
                koniec_procesu = czas + okres_procesu-1;
                //Pętla wykonująca proces
                for (int j = 1; j <= okres_procesu; j++) {
                    System.out.println("Proces " + nazwa + " [" + czas_przyjscia + ", " + okres_procesu + "] = " + czas + "s");
                    czas++;
                }
                //Wyświetlanie czasu rozpoczęcia procesu i zakończenia po jego wykonaniu
                System.out.println("Początek procesu " + nazwa + ": " + poczatek_procesu + "s Koniec procesu: " + koniec_procesu + "s\n");
                licznik+=okres_procesu;
                okres_procesu = 0;
                ilosc_procesow++;
                //System.out.println("Ilosć procesów zakończonych: " + ilosc_procesow);
                czy_proces = false;

                // Sprawdzenie, czy są nowe procesy, które nadeszły w trakcie wykonywania poprzedniego procesu
                for (int i = poczatek_procesu; i <= koniec_procesu; i++) {
                    for (Proces proces : procesy) {
                        //Dodawanie do kolejki
                        if (proces.getCzas_nadejscia() == i && !proces.getNazwa().equals(nazwa)) {
                            kolejka.add(proces);
                            //Dodawanie do kolejki wypisywane na ekran do sprawdzania poprawnego działania programu
                            System.out.println("Dodawanie do kolejki procesu: " + proces.getNazwa());
                        }
                    }
                }
            }

            // Jeśli kolejka jest pusta, a w danym czasie nie został zlecony żaden proces, program trwa dalej licząc czas który upłynął
            else if (kolejka.isEmpty() && ilosc_procesow!=liczba) {
                System.out.println("Minął czas " + czas + "s");
                czas++;
            }
        }
        licznik--;
        //Obliczanie średniej
        float srednia = (float) licznik / liczba;

        //licznik dla odchylenia standardowego
        float o = 0;
        for (Proces proces : procesy) {
            o += (float) Math.pow(proces.getOkres() - srednia, 2);
        }
        float odchylenie = (float) Math.sqrt(o / liczba);

        //Wyświetlanie ostatecznych statystyk programu
        System.out.println("Czas wykonania wszystkich procesów: " + licznik + "s");
        System.out.println("Średni okres wykonywania jednego procesu: " + srednia + "s");
        System.out.println("Odchylenie standardowe procesów: " + odchylenie);
    }
}
