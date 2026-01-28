import java.util.Scanner;

class Ennemy {
    public char[][] gridEnnemyGen(){
        char grid[][];
        int boats[];
        int x;
        int y;

        grid = new char[10][10];
        boats = new int[] {5, 4, 3, 3, 2, 0};
        for (int i = 0; i < 10; i++) // Commence l'initialisation à 0
            for(int j = 0; j < 10; j++) // Assigne 0 sur chaque caracteres de la ligne en cours (j)
                grid[i][j] = '0';
        for (int i = 0; i < 5;)
        {
            x = (int)(Math.random() * 10);
            y =	(int)(Math.random() * 10);
            if (gridEnnemyChecker(grid, x, y, boats[i]) == 1)
                i++;
        }
        return grid;
    }
    public int	gridEnnemyChecker(char[][] grid, int x, int y, int size)
    {
        int	i;
        int	j;

        j = x;
        i = y;
        while (j < 10 && grid[j][i] == '0')
        {
            if (j == x + size)
            {
                gridEnnemyWriter(grid, x, y, 1, size);
                return (1);
            }
            j++;
        }
        j = x;
        while (i < 10 && grid[j][i] == '0')
        {
            if (i == y + size)
            {
                gridEnnemyWriter(grid, x, y, 0, size);
                return (1);
            }
            i++;
        }
        return (0);
    }
    public void	gridEnnemyWriter(char grid[][], int x, int y, int way, int size)
    {
        int		limit;
        char	c;

        c = '5';
        if (size == 2)
            c = '2';
        else if (size == 3)
            c = '3';
        else if (size == 4)
            c = '4';
        limit = y + size;
        while (way == 0 && y != limit)
            grid[x][y++] = c;
        limit = x + size;
        while (way == 1 && x != limit)
            grid[x++][y] = c;
    }
}

class Player{
    public char[][]	gridUserGen()
    {
        char grid[][];
        int i;
        int j;

        grid = new char[10][10];
        i = 0;
        j = 0;
        while (i != 10)
        {
            while (j != 10)
                grid[i][j++] = ' ';
            i++;
            j = 0;
        }
        return grid;
    }
}

class Game{
    int x;
    int y;
    double touched_shot = 0;
    double total_shot = 0;
    double win_rate;

    public boolean move_parser(Game pos, String move)
    {
        String 	alphabet = "ABCDEFGHIJ";

        for (int i = 0; i < move.length();){
            if (i == 0 && move.charAt(i) >= 'A' && move.charAt(i) <= 'J')
                i++;
            else if (i > 0 && move.charAt(i) >= '0' && move.charAt(i) <= '9')
                i++;
            else
                return false;
        }
        for (int i = 0; i < alphabet.length(); i++){
            if (move.charAt(0) == alphabet.charAt(i))
                pos.y = i;
        }
        pos.x = Integer.parseInt(move.substring(1)) - 1;
        return true;
    }

    public void play_move(char ennemy[][], char player[][], Game pos)
    {
        char boat;

        boat = ennemy[pos.x][pos.y];
        String POS = "\033[%d;%dH";
        if (player[pos.x][pos.y] != ' ')
        {
            System.out.printf(POS + "\033[1;33mALREADY PLAYED... RETRY", 5, 48);
            return ;
        }
        else if (ennemy[pos.x][pos.y] != '0')
        {
            pos.total_shot++;
            pos.touched_shot++;
            player[pos.x][pos.y] = 'X';
            ennemy[pos.x][pos.y] = '0';
            if (sunk_checker(ennemy, player, boat) == true)
                System.out.printf(POS + "\033[1;31m⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" + //
                                POS +	"\033[1;33mSUNK!\033[1;31m     ⠀⠀⠀⢀⣄⠈⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" + //
                                POS +	"⠀⠀⣴⣄⠀⢀⣤⣶⣦⣀⠀⠀⣰⣿⣿⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" + //
                                POS +	"⠀⠀⢸⣿⣷⣌⠻⢿⣩⡿⢷⣄⠙⢿⠟⠀⠀⠀⠀⠀⠰⣄⠀⠀⠀⠀⠀⠀⠀\n" + //
                                POS +	"⠀⠀⠈⣿⣿⣿⣷⣄⠙⢷⣾⠟⣷⣄⠀⠀⠀⠀⣠⣿⣦⠈⠀⠀⠀⠀⠀⠀⠀\n" + //
                                POS +	"⠀⠀⠀⢿⣿⣿⣿⣿⣷⣄⠙⢿⣏⣹⣷⣄⠀⢴⣿⣿⠃⠀⠀⠀⠀⢀⡀⠀⠀\n" + //
                                POS +	"⠀⠀⠸⣦⡙⠻⣿⣿⣿⣿⣷⣄⠙⢿⣤⡿⢷⣄⠙⠃⠀⠀⠀⠀⣀⡈⠻⠂⠀\n" + //
                                POS +	"⠀⠀⠀⠈⠻⣦⡈⠻⣿⣿⣿⣿⣷⣄⠙⢷⣾⠛⣷⣄⠀⠀⢀⣴⣿⣿⠀⠀⠀\n" + //
                                POS +	"⠀⠀⠀⠀⠀⠈⠻⣦⡈⠛⠛⠻⣿⣿⣷⣄⠙⠛⠋⢹⣷⣄⠈⠻⠛⠃⠀⠀⠀\n" + //
                                POS +	"⢀⣴⣿⣧⡀⠀⠀⠈⢁⣼⣿⣄⠙⢿⡿⠋⣠⣿⣧⡀⠠⡿⠗⢀⣼⣿⣦⡀⠀\n" + //
                                POS +	"⠟⠛⠉⠙⠻⣶⣤⣶⠟⠋⠉⠛⢷⣦⣴⡾⠛⠉⠙⠻⣶⣤⣶⠟⠋⠉⠛⠻⠀\n" + //
                                POS +	"⣶⣿⣿⣿⣦⣄⣉⣠⣶⣿⣿⣷⣦⣈⣁⣴⣾⣿⣿⣶⣄⣉⣠⣶⣿⣿⣿⣶⠀", 4, 48, 5, 48, 6, 48, 7, 48, 8, 48, 9, 48, 10, 48, 11, 48, 12, 48, 13, 48, 14, 48, 15, 48);
            else
                System.out.printf(POS + "\033[1;33mHIT !                  ", 5, 48);
        }
        else
        {
            pos.total_shot++;
            System.out.printf(POS + "\033[1;33mMISSED...              ", 5, 48);
            player[pos.x][pos.y] = 'O';
            ennemy[pos.x][pos.y] = '0';
        }
        pos.win_rate = (100 * pos.touched_shot) / pos.total_shot;
        Main.grid_viewer(player);
    }

    public boolean sunk_checker(char ennemy[][], char player[][], char boat)
    {
        int count;

        count = 0;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                if (ennemy[i][j] == boat && player[i][j] == ' ')
                    count++;
        if (boat == '3' && count == 3)
            return true;
        else if (count != 0)
            return false;
        return true;
    }

    public boolean end_checker(char ennemy[][], char player[][])
    {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                if (ennemy[i][j] != '0')
                    return false;
        return true;
    }

    public void game(char ennemy[][], char player[][])
    {
        String POS = "\033[%d;%dH";
        Scanner scanner = new Scanner(System.in);
        Game pos = new Game();

        for (int i = 0; pos.total_shot < 100; i++)
        {
            pos.x = 0;
            pos.y = 0;
            System.out.printf(POS + "\033[1;33mTotal shot : %.0f", 17, 48, pos.total_shot);
            System.out.printf(POS + "\033[1;33mTouched : %.0f", 19, 48, pos.touched_shot);
            System.out.printf(POS + "\033[1;33mWin rate : %2.2f%%", 21, 48, pos.win_rate);
            System.out.printf(POS + "\033[1;33mPosition to shoot : ", 3, 48);
            if (end_checker(ennemy, player) == true)
            {
                if (pos.win_rate < 30)
                    System.out.printf(POS + "\033[1;33mYOU LOSE !", 5, 48);
                else
                    System.out.printf(POS + "\033[1;33mYOU WON !", 5, 48);
                System.out.printf("\033[24;80H\n");
                return ;
            }
            String input = scanner.nextLine();
            System.out.printf(POS + "                               " + //
                                POS +	"                               " + //
                                POS +	"                               " + //
                                POS +	"                               " + //
                                POS +	"                               " + //
                                POS +	"                               " + //
                                POS +	"                               " + //
                                POS +	"                               " + //
                                POS +	"                               " + //
                                POS +	"                               " + //
                                POS +	"                               " + //
                                POS +	"                               ", 4, 48, 5, 48, 6, 48, 7, 48, 8, 48, 9, 48, 10, 48, 11, 48, 12, 48, 13, 48, 14, 48, 15, 48);
            if (input.equals("q") || input.equals("exit") || input.equals("Q"))
            {
                System.out.printf(POS + "LEAVING...", 5, 48);
                System.out.printf("\033[24;80H\n");
                return ;
            }
            System.out.printf(POS + "     ", 3, 68);
            if (input.length() == 0 || input.length() == 1 || move_parser(pos, input) == false || pos.x < 0 || pos.x >= 10 || pos.y > 10)
            {
                System.out.printf(POS + "WRONG MOVEMENT, RETRY", 5, 48);
                continue ;
            }
            play_move(ennemy, player, pos);
        }
    }
}

public class Main {
    public static void main(String[] args){
        Ennemy ennemyGrid = new Ennemy();
        Player playerGrid = new Player();
        Game playGame = new Game();


        frame();
        playGame.game(ennemyGrid.gridEnnemyGen(), playerGrid.gridUserGen());
        return ;
    }
    public static void frame(){
        int i = 4;
        int j = 4;
        int k = 0;
        int l = 1;
        String POS = "\033[%d;%dH";
        String CLEAR = "\033[2J\033[1;95m";
        String ALPHA = "ABCDEFGHIJ";
        System.out.printf(CLEAR);
        while (i < 10 * 4 + 2)
        {
            while (j <= 10 * 2 + 2)
            {
                System.out.printf(POS + "───┼" + POS + "│", j, i, j - 1, i + 3);
                System.out.printf(POS + "│" + POS + "├" + POS + "│" + POS + "┤", j - 1, 3, j, 3, j - 1, 10 * 4 + 3, j, 10 * 4 + 3);
                System.out.printf(POS + "%d", j - 1, 1, l++);
                j += 2;
            }
            System.out.printf(POS + "%c", 1, i + 1, ALPHA.charAt(k++));
            System.out.printf(POS + "───┬" + POS + "───┴", 2, i, 10 * 2 + 2, i);
            j = 4;
            i += 4;
            l = 1;
        }
        System.out.printf(POS + "╭" + POS + "╰" + POS + "─╮" + POS + "─╯", 2, 3, 10 * 2 + 2, 3, 2, 10 * 4 + 2, 10 * 2 + 2, 10 * 4 + 2);
    }
    public static void	grid_viewer(char grid[][])
    {
        String POS = "\033[%d;%dH";

        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
                System.out.printf(POS + "\033[1;32m%c\033[0m", i * 2 + 3, j * 4 + 5, grid[i][j]);
            System.out.printf("\n");
        }
        System.out.printf(POS + "\n", 23, 80);
    }
}