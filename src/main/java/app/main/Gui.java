package app.main;

import app.config.Config;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class represents the GUI which starts selenium testcases, print logs and
 * show results
 * 
 * @author Michael Bulinski
 */

public class Gui extends Application {

	private final Button startButton = new Button();
	private final ToggleGroup company_group = new ToggleGroup();

	private Label testcase_count;
	private Label correct_count;
	private Label wrong_count;

	private TextArea center_text_area;
	private TextArea right_text_area;

	private Config config = new Config();
	private String default_url = "";
	private String testcase_file_path = "";
	private String company_name = "";

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane border = new BorderPane();

		border.setTop(addTopBox());
		border.setCenter(addCenterBox());
		border.setBottom(addBottomBox());

		Scene scene = new Scene(border, 1400, 590);
		primaryStage.setScene(scene);
		primaryStage.setTitle("RailwayApplication_Test");
		primaryStage.setResizable(false);
		primaryStage.show();

		company_group.selectedToggleProperty()
				.addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
					if (new_toggle != null) {
						if (new_toggle.toString().contains(config.getCompany_pkp())) {
							this.default_url = config.getPkp_url();
							this.testcase_file_path = config.getPkp_testcase_path();
							this.company_name = config.getCompany_pkp();
						} else if (new_toggle.toString().contains(config.getCompany_rmv())){
							this.default_url = config.getRmv_url();
							this.testcase_file_path = config.getRmv_testcase_path();
							this.company_name = config.getCompany_rmv();
						}
					}
				});

		startButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (!startButton.isDisabled()) {
							testcase_count.setText("0");
							correct_count.setText("0");
							wrong_count.setText("0");

							TestStarter start_test = new TestStarter(default_url, center_text_area,
								right_text_area, testcase_count, correct_count, wrong_count, testcase_file_path, company_name);
							start_test.start();
						}
					}
				});
			}
		});

	}

	public HBox addTopBox() {

		this.default_url = config.getPkp_url();
		this.testcase_file_path = config.getPkp_testcase_path();
		this.company_name = config.getCompany_pkp();

		HBox headbox = new HBox();
		headbox.setPadding(new Insets(5, 5, 5, 5));
		headbox.setSpacing(10);

		startButton.setStyle("-fx-font: 16 arial; -fx-base: #e80d0d;");
		startButton.setText("Start");

		final Label empty_label_left = new Label("     ");
		final RadioButton pkp_radiobutton = new RadioButton("PKP - Polskie Koleje Panstwowe");
		final RadioButton rmv_radiobutton = new RadioButton("RMV - Rhein-Main-Verkehrsverbund");

		empty_label_left.setPadding(new Insets(5, 5, 5, 5));
		pkp_radiobutton.setPadding(new Insets(5, 5, 5, 5));
		rmv_radiobutton.setPadding(new Insets(5, 5, 5, 5));

		pkp_radiobutton.setToggleGroup(company_group);
		pkp_radiobutton.setSelected(true);
		rmv_radiobutton.setToggleGroup(company_group);

		GridPane gridPane = new GridPane();
		gridPane.add(startButton, 0, 0);
		gridPane.add(empty_label_left, 1, 0);
		gridPane.add(pkp_radiobutton, 2, 0);
		gridPane.add(rmv_radiobutton, 3, 0);

		headbox.getChildren().add(gridPane);
		return headbox;
	}

	public SplitPane addCenterBox() {
		SplitPane splitPane = new SplitPane();

		SplitPane splitBox = new SplitPane();
		splitBox.setPadding(new Insets(3, 3, 3, 3));

		VBox loggingbox = new VBox();
		loggingbox.setPadding(new Insets(3, 3, 3, 3));
		loggingbox.setSpacing(10);

		center_text_area = new TextArea();
		center_text_area.setMinHeight(500.0);
		center_text_area.setMaxHeight(500.0);
		center_text_area.setEditable(false);
		center_text_area.setWrapText(true);

		loggingbox.getChildren().add(center_text_area);

		splitBox.setOrientation(Orientation.HORIZONTAL);
		splitBox.getItems().addAll(loggingbox);
		splitBox.setDividerPosition(0, 0.20);

		VBox resultbox = new VBox();
		resultbox.setPadding(new Insets(6, 5, 5, 5));
		resultbox.setSpacing(10);

		right_text_area = new TextArea();
		right_text_area.setMinHeight(500.0);
		right_text_area.setMaxHeight(500.0);
		right_text_area.setEditable(false);
		right_text_area.setWrapText(true);
		resultbox.getChildren().add(right_text_area);

		splitPane.setOrientation(Orientation.HORIZONTAL);
		splitPane.getItems().addAll(splitBox, resultbox);
		splitPane.setDividerPosition(0, 0.80);
		splitBox.minWidthProperty().bind(splitPane.widthProperty().multiply(0.80));
		splitBox.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.80));

		return splitPane;
	}

	private HBox addBottomBox() {
		HBox bottombox = new HBox();
		bottombox.setPadding(new Insets(5, 5, 5, 5));
		bottombox.setSpacing(10);

		final Label result_label = new Label("Test Results");
		final Label result_count_label = new Label("Test Cases :");
		final Label correct_count_label = new Label("Successful :");
		final Label wrong_count_label = new Label("Failed :");

		testcase_count = new Label("0");
		correct_count = new Label("0");
		wrong_count = new Label("0");

		result_label.setPadding(new Insets(5, 5, 5, 5));
		result_count_label.setPadding(new Insets(5, 5, 5, 5));
		testcase_count.setPadding(new Insets(5, 5, 5, 5));
		correct_count_label.setPadding(new Insets(5, 5, 5, 5));
		correct_count.setPadding(new Insets(5, 5, 5, 5));
		wrong_count_label.setPadding(new Insets(5, 5, 5, 5));
		wrong_count.setPadding(new Insets(5, 5, 5, 5));

		correct_count_label.setTextFill(Color.GREEN);
		wrong_count_label.setTextFill(Color.RED);

		GridPane gridPane = new GridPane();
		gridPane.add(result_label, 0, 0);
		gridPane.add(result_count_label, 1, 0);
		gridPane.add(testcase_count, 2, 0);
		gridPane.add(correct_count_label, 3, 0);
		gridPane.add(correct_count, 4, 0);
		gridPane.add(wrong_count_label, 5, 0);
		gridPane.add(wrong_count, 6, 0);

		bottombox.getChildren().add(gridPane);
		return bottombox;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
