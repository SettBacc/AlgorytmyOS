import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int liczba = scanner.nextInt(); //liczba stron podana przez użytkownika
        int[] strony = new int[liczba]; //tablica zapisująca numer każdej strony

        //Generowanie numerów stron
        for (int i=0;i<liczba;i++)
        {
            int numer = new Random().nextInt(10)+1;
            strony[i]=numer;
            System.out.print(numer + " ");
        }

        int[] miejsca = new int[3];//tablica z ilością miejsc dla numerów
        int[] czas = new int[3];//tablica licząca czas przechowywania numerów
        int czas_ogolny=0;

        //wypisanie początkowych miejsc
        System.out.println("\n\n"+czas_ogolny+" sekunda: [" + miejsca[0] + ", " + miejsca[1] + ", " + miejsca[2]+"]");
        czas_ogolny++;

        for (int i=0;i<liczba;i++) {
            boolean zmiana = true;

            for (int j=0;j<miejsca.length;j++)
            {
                //sprawdzanie, czy numer już jest na jednym z miejsc
                if (miejsca[j] == strony[i])
                {
                    zmiana=false;
                    czas[j] = czas_ogolny;
                    break;
                }
            }

            //jeżeli żaden z numerów na miejscach nie odpowiada nowemu numerowi to najstarszy dodany zostaje zastąpiony
            if (zmiana)
            {
                int najstarszy_tab = LastRecent(czas);
                miejsca[najstarszy_tab]=strony[i]; //zamiana najstarszego numeru na nowy
                czas[najstarszy_tab]=czas_ogolny; //ustawianie czasu dodania nowego numeru na czas programu
            }
            System.out.println("\n"+czas_ogolny+" sekunda, numer ["+strony[i]+"]: [" + miejsca[0] + ", " + miejsca[1] + ", " + miejsca[2]+"]");
            System.out.println("Czas dodania numerów "+miejsca[0]+":["+czas[0]+"s] "+miejsca[1]+":["+czas[1]+"s] "+miejsca[2]+":["+czas[2]+"s]");
            czas_ogolny++;
        }
    }
    //funkcja sprawdzająca, który numer jest najstarszy
    static int LastRecent(int[] czas) {
        int najstarszy = czas[0]; //najstarszy czas
        int najstarszy_tab = 0; //indeks najstarszego czasu

        //sprawdzanie, który jest najstarszy
        for (int i=1;i<czas.length;i++)
        {
            if (czas[i]<najstarszy)
            {
                najstarszy=czas[i];
                najstarszy_tab=i;
            }
        }

        return najstarszy_tab;
    }
}
