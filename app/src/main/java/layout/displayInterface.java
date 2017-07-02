package layout;

/** Interface that allows communication between StandardCellAdapter and Websites class
 *  Required because the SwipeRevealLayout library interferes with the onClickListeners in the Websites class
 *  StandardCellAdapter can track which cell is clicked and the information inside to create the new view request via Websites.java
 * Created by Praveen on 12/27/16.
 */

public interface displayInterface {
    void displayWebpage(String Link);
}
