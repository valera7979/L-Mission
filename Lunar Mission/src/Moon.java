import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Moon
{
    public static final double GRAVITY = 1.6;
    public static final double CRITICAL_VELOCITY = 2.5;
    public static  final double SECOND_CRITICAL_VELOCITY = 5.0;
    public static  final int MOVE_DURATION = 5;
    public static  final double INIT_ROCKET_MASS = 1860;
    public static  final double INIT_FUEL_MASS = 140;
    public static  final double INIT_HEIGHT = 100;
    public static  final double INIT_VELOCITY = 0;

    public static void main(String[] args)
    {
        Moon moon = new Moon();
        Rocket rocket = new Rocket(INIT_ROCKET_MASS, INIT_FUEL_MASS, INIT_HEIGHT, INIT_VELOCITY);

        System.out.println();
        moon.tellStory();

        while (!rocket.isLanded()) {
            moon.printParameters(rocket);
            Double fuel;
            if (rocket.getFuel_mass()>0)
            {
                System.out.print("Введите количество топлива: ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                try
                {
                    fuel = Double.parseDouble(reader.readLine());
                    if (fuel>rocket.getFuel_mass()) {
                        System.out.println("Топливо закончилось. В двигатель залит весь остаток - " + rocket.getFuel_mass());
                        fuel = rocket.getFuel_mass();
                    }
                }
                catch (Exception e) {
                    System.out.println("!!! Ошибка в топливном отсеке !!! Не удалось залить топливо");
                    fuel = 0.0;
                }
            }
            else {
                fuel = 0.0;
                moon.printSlowly("Топливо закончилось, двигатели не работают...", 20);
            }
            rocket.countParameters(fuel);

        }

        System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");
        System.out.println(String.format("                 Корабль прилунился со скоростью - %.2f м/с\n"
                                        , Math.abs(rocket.getVelocity())));
        if (rocket.getVelocity()>-CRITICAL_VELOCITY) System.out.println("                         Отлично! Это была мягкая посадка\n");
        else if (rocket.getVelocity()>-SECOND_CRITICAL_VELOCITY) System.out.println("                 Хорошо. Было немного жестко, но никто не пострадал\n");
        else System.out.println("                            Ужасно!!! Корабль разбился");
        System.out.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");


    }

    private void tellStory()
    {
        this.printSlowly("В далекой, далекой галактике .... ...            ", 20);
        this.printSlowly("Вам нужно прилуниться на Луну со скоростью не больше 5 м/с. Посадка на 'отлично' не больше 2.5 м/с ");

        this.printSlowly("Начальные уловия: ");
        this.printSlowly("Вес корабля: " + INIT_ROCKET_MASS + " kg   Запас топлива: " + INIT_FUEL_MASS + " kg");
        this.printSlowly("Высота над поверхностью: " + INIT_HEIGHT + " м.    Начальная скорость: " + INIT_VELOCITY + " м/с. \n" +
        "Если скорость со знаком минус, значит корабль снижается. Положительная скорость означает что корабль летит вверх.",20);

        this.printSlowly("Ваша задача подливая топливо влиять на работу двигателя таким образом " +
                "чтобы корабль не разбился (скорость не больше 5 м/с)", 20);
        System.out.println("У Д А Ч И !!!");
    }

    private void printParameters(Rocket rocket)
    {
        System.out.println("__________________________________________");
        System.out.println("| ТОПЛИВО, L | ВЫСОТА, m | СКОРОСТЬ, m/s |");
        System.out.println("------------------------------------------");
        System.out.println("|     " + (int)rocket.getFuel_mass() + "    |    " + (int)rocket.getHeight()
                            + "    |     " + String.format("%.2f",rocket.getVelocity()) + "      |");
        System.out.println("-----------------------------------------");

    }


    void printSlowly(String s) {
        for (int i=0; i<s.length(); i++) {
            System.out.print(s.charAt(i));
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    void printSlowly(String s, int time) {
        for (int i=0; i<s.length(); i++) {
            System.out.print(s.charAt(i));
            try
            {
                Thread.sleep(time);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println();
    }


}
