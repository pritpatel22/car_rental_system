package p1;

public class Car {
    int id, seat, noOfCar;
    String name;
    double ppkm;

    public void setName(String name) {
        this.name = name;
    }

    public void setPPKM(double ppkm) {
        this.ppkm = ppkm;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setNoOfCar(int noOfCar) {
        this.noOfCar = noOfCar;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPPKM() {
        return ppkm;
    }

    public int getSeat() {
        return seat;
    }

    public int getNoOfCar() {
        return noOfCar;
    }
}
