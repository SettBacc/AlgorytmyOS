class Proces {
    final private String nazwa;
    final private int czas_nadejscia;
    final private int okres;

    public Proces(String nazwa, int czas_nadejscia, int okres) {
        this.nazwa = nazwa;
        this.czas_nadejscia = czas_nadejscia;
        this.okres = okres;
    }

    public int getCzas_nadejscia() {
        return czas_nadejscia;
    }
    public int getOkres()
    {
        return  okres;
    }
    public String getNazwa()
    {
        return nazwa;
    }

    public String toString() {
        return "Proces " + nazwa + " [" + czas_nadejscia + ", " + okres + "]";
    }
}