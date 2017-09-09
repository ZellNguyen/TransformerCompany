/**
 * Created by hoa_nguyen on 2017-09-08.
 */
public class BattleInterface {
    public static void main(String[] args) {
        Battle battle = new Battle();

        battle.start();

        if(battle.isAllDestroyed)
            System.out.println("All transformers are destroyed!");
        else {
            if(battle.getWinner() == null)
                System.out.println("Tie game");
            else {
                System.out.println("Number of battles: " + battle.numberOfBattles());
                System.out.println("Winning team: " + battle.getWinner());
                System.out.print("Survivors from the losing team: ");
                for (Transformer tf : battle.getSurvivors())
                    System.out.print(tf.name + ", ");
            }
        }
    }
}
