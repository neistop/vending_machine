import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {
    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private final CoinAcceptor coinAcceptor;
    private final BillAcceptor billAcceptor;
    private final CardAcceptor cardAcceptor;
    private static int mode = 0;
    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        coinAcceptor = new CoinAcceptor(100);
        billAcceptor = new BillAcceptor(1);
        cardAcceptor = new CardAcceptor(10);
    }

    public static void run() {
        AppRunner app = new AppRunner();

        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("\nВ автомате доступны:");
        showProducts(products);

        if (mode == 0) {
            print("\nМонет на сумму: " + coinAcceptor.getAmount());
        } else if (mode == 1) {
            print("\nДенег: " + (billAcceptor.getAmount()) / billAcceptor.getBillOnCoins());
        } else if (mode == 2) {
            print("\nБаланс карты: " + (cardAcceptor.getAmount()) / cardAcceptor.getCardBillOnCoins());
        }

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();

        for (int i = 0; i < products.size(); i++) {
            if (mode == 0) {
                if (coinAcceptor.getAmount() >= products.get(i).getPrice()) {
                    allowProducts.add(products.get(i));
                }
            } else if (mode == 1) {
                if (billAcceptor.getAmount() >= products.get(i).getPrice()) {
                    allowProducts.add(products.get(i));
                }
            } else if (mode == 2) {
                if (cardAcceptor.getAmount() >= products.get(i).getPrice()) {
                    allowProducts.add(products.get(i));
                }
            }
        }

        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");

        if (mode == 0) {
            print(" i - Сменить на деньгоприёмник (купюроприёмник)");
            print(" j - Сменить на карто-приёмник");
        } else if (mode == 1) {
            print(" g - Сменить на монетоприёмник");
            print(" j - Сменить на карто-приёмник");
        } else if (mode == 2) {
            print(" g - Сменить на монетоприёмник");
            print(" i - Сменить на деньгоприёмник (купюроприёмник)");
        }

        System.out.print("\nВведите действие: ");
        String action = fromConsole().substring(0, 1);

        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    if (mode == 0) {
                        coinAcceptor.setAmount(coinAcceptor.getAmount() - products.get(i).getPrice());
                    } else if (mode == 1) {
                        billAcceptor.setAmount(billAcceptor.getAmount() - products.get(i).getPrice());
                    } else if (mode == 2) {
                        cardAcceptor.setAmount(cardAcceptor.getAmount() - products.get(i).getPrice());
                    }
                    print("Вы купили " + products.get(i).getName());
                    break;
                } else if ("a".equalsIgnoreCase(action)) {
                    System.out.print("Введите сумму пополнения: ");
                    if (mode == 0) {
                        coinAcceptor.setAmount(coinAcceptor.getAmount() + Integer.parseInt(fromConsole()));
                    } else if (mode == 1) {
                        billAcceptor.setAmount(billAcceptor.getAmount() + (Integer.parseInt(fromConsole())) * billAcceptor.getBillOnCoins());
                    } else if (mode == 2) {
                        cardAcceptor.setAmount(cardAcceptor.getAmount() + (Integer.parseInt(fromConsole())) * cardAcceptor.getCardBillOnCoins());
                    }
                    break;
                } else if ("g".equalsIgnoreCase(action)) {
                    mode = 0;
                    break;
                } else if ("i".equalsIgnoreCase(action)) {
                    mode = 1;
                    break;
                } else if ("j".equalsIgnoreCase(action)) {
                    mode = 2;
                    break;
                } else if ("h".equalsIgnoreCase(action)) {
                    isExit = true;
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попрбуйте еще раз.");
            chooseAction(products);
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}