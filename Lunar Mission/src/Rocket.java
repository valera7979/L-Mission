public class Rocket
{

    public static final double GAS_VELOCITY = 1000;

    private double rocket_mass;
    private double height;
    private double velocity;
    private double fuel_mass;

    public Rocket(double rocket_mass, double fuel_mass, double height, double velocity)
    {
        this.rocket_mass = rocket_mass;
        this.height = height;
        this.velocity = velocity;
        this.fuel_mass = fuel_mass;
    }

    public double getRocket_mass()
    {
        return rocket_mass;
    }

    public double getHeight()
    {
        return height;
    }

    public double getVelocity()
    {
        return velocity;
    }

    public double getFuel_mass()
    {
        return fuel_mass;
    }


    public boolean isLanded()
    {
        return height <= 0;
    }

    public void countParameters(Double fuel)
    {
        // движение ракеты в гравитационном поле http://stu.alnam.ru/book_mor-173
        // переменные заменены на короткие чтобы легче было писать громоздкую формулу
        // если возникнет ошибка проверять формулу по книжке будет легче
        double v = velocity;
        double h = height;
        double f = fuel;
        double m = rocket_mass + fuel_mass - f;
        double m0 = rocket_mass + fuel_mass;
        int t = Moon.MOVE_DURATION;
        double fps = fuel / t;  // fuel per second
        double dt = 0.05;
        double precise_dt = 0.005;

        // System.out.print("HEIGHT LOG: ");

        // нельзя использовать формулу для больших промежутков времени, т.к. может возникнуть ситуация когда ракета
        // уйдет ниже поверхноти, а потом вернется обратно. чтобы зафиксировать контакт с поверхностью, нужно считать маленькими шагами
        for (double i = 0.0; i <= t; i = i + dt)
        {
            double h_before = h;
            double v_before = v;

            if (fuel > 0.0)
                h += ((v + GAS_VELOCITY) * fps * dt + GAS_VELOCITY * (m0 - fps * dt) * Math.log((m0 - fps * dt) / m0)) / fps -
                        Moon.GRAVITY * dt * dt / 2;
            else h += v * dt - Moon.GRAVITY * dt * dt / 2;

            v += GAS_VELOCITY * Math.log(m0 / (m0 - fps * dt)) - Moon.GRAVITY * dt;

            m0 -= fps * dt;
            //   System.out.print(String.format("%.2f", h) + " ");

            //если пройдена нулевая точка делается возврат на шаг назад и рассчитывается более тщательно
            if ((h <= 0) && (dt != precise_dt))
            {
                i -= dt;
                dt = precise_dt;
                h = h_before;
                v = v_before;
            }

            if (h <= 0)
                break;
        }
        fuel_mass -= fuel;
        height = h;
        velocity = v;
        System.out.println();
    }
}
