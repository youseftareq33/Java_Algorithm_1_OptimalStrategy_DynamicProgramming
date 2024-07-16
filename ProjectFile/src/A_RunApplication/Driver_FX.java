package A_RunApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import B_Classes.*;
import C1_StroreData_LinkedList.LinkedList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;


public class Driver_FX extends Application {
	
	// for fx
	Stage stage_fx=new Stage();
	DropShadow shadow = new DropShadow();
	
	
	// for file
	City arr_city[][]=null;
	String typeOfSol="";
	String filePath;
	String fileData;
	int numOfCities;
	int total_cost=0;
	String start;
	String end;
	String cbEnd_selectedItem;
	
	File file;

	ObservableList<String> city_ol_start;
	ObservableList<String> city_ol_end;
	
	ObservableList<City> city_dataList;
	
	LinkedList<String> ll_path=new LinkedList<String>();
	
	private TableView<String[]> dpTable = new TableView<>();
    private TextArea resultTextArea = new TextArea();
    private int number_of_cities;
    private String[] city;
    private int[][] dp;
    private String[][] Citydp;
    private String str;
	
	// for Pages
	Scene scene1_mainP;
	Scene scene2_solutionP;
	
	@Override
	public void start(Stage primaryStage) {
		
		//pages
		scene1_mainP = new Scene(MainPage(),1700,800);
		
		
		// stage
		stage_fx.setMaximized(true);
		stage_fx.setScene(scene1_mainP);
		stage_fx.setTitle("Optimal Strategy for Minimum cost Dynamic");
		stage_fx.setIconified(true);
		stage_fx.getIcons().add(new Image(getClass().getResourceAsStream("/D2_Graphic_icons/appIcon.png")));
		
		stage_fx.show();
	}
	
	
	
	//=== Page --------------------------------------------------------
	
	// Main Page
	public BorderPane MainPage() {
		BorderPane bp_mainPage = new BorderPane();
		
		
		
		// Top *********************************************************
		VBox vb_Top=new VBox(); // Contains hb_topTop+hb_invalidChooseFile
		/*
		 * hb_topTop            : label(l1)+button(b_loadfile)
		 * 
		 * hb_invalidChooseFile : Text(t_err_invalidChooseFile)
		*/
		
		//==================================
		
		//Top_Top
		HBox hb_topTop=new HBox();
		//***
		Label l1=new Label();
		l1.setWrapText(true);
		l1.setPrefSize(570, 55);
		l1.setStyle("-fx-background-color: white;-fx-Border-color: #C0C0C0;-fx-Border-width:5;"
				+ "-fx-font-size:15;-fx-font-weight:bold;-fx-text-fill: LIMEGREEN;-fx-alignment: center;");
		//***
		Button b_loadfile=new Button("Load File");
		b_loadfile.setPrefSize(125, 43);
		b_loadfile.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-Border-color: 	#48D1CC;"
				+ "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:5;-fx-font-weight:bold; -fx-text-fill: #48D1CC");

		b_loadfile.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            b_loadfile.setEffect(shadow);
		            b_loadfile.setStyle("-fx-background-color: #48D1CC; -fx-background-radius: 15; -fx-Border-color: #48D1CC;"
		        			+ "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:5;-fx-font-weight:bold; -fx-text-fill: 	white");
		          }
		        });
		b_loadfile.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            b_loadfile.setEffect(null);
		            b_loadfile.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-Border-color: 	#48D1CC;"
		        			+ "-fx-Border-radius: 10;-fx-font-size:20;-fx-Border-width:5;-fx-font-weight:bold; -fx-text-fill: #48D1CC");
		          }
		        });
		
		
		hb_topTop.getChildren().addAll(l1,b_loadfile);
		hb_topTop.setSpacing(20);
		hb_topTop.setPadding(new Insets(45, 0, 20, 420)); //top, right, bottom, left
		
		vb_Top.getChildren().add(hb_topTop);
		
		
		//-------------

		//Bottom_Top
		HBox hb_invalidChooseFile=new HBox();
		Text t_err_invalidChooseFile=new Text();
		t_err_invalidChooseFile.setStyle("-fx-font-weight:bold;");
		hb_invalidChooseFile.getChildren().add(t_err_invalidChooseFile);
		hb_invalidChooseFile.setPadding(new Insets(-15, 0, 0, 420));

		
		
		
		
		
		
		// Center ******************************************************	
		VBox vb_center=new VBox(); // Contains hb_topTop_Center+hb_Bottom_Center
		/*
		 * hb_topTop_Center : TextArea(ta2)
		 * 
		 * hb_Bottom_Center : GridPane(gp_bottom_Center--> Text(t_start)+ComboBox(cb_start)
		 * 												   Text(t_end)+ComboBox(cb_end) )
		*/
		
		//==================================
		
		//Top_Center
		HBox hb_topTop_Center=new HBox();
		hb_topTop_Center.setAlignment(Pos.CENTER_LEFT);
		
		TextArea ta2=new TextArea();
		ta2.setPrefSize(700, 400);
		ta2.setWrapText(true);
		ta2.setEditable(false);
		ta2.setStyle("-fx-Border-color: #C0C0C0;-fx-Border-width:1;");
		
		hb_topTop_Center.getChildren().add(ta2);
		hb_topTop_Center.setPadding(new Insets(40, 0, 15, 425));
		
		//-------------

		//Center_Center
		
		HBox hb_center_Center=new HBox();
		Text t_successDataLoad=new Text();
		t_successDataLoad.setStyle("-fx-font-weight:bold;-fx-font-size:13;-fx-text-fill:MEDIUMSPRINGGREEN;");
		hb_center_Center.getChildren().add(t_successDataLoad);
		hb_center_Center.setPadding(new Insets(-10, 0, 5, 430));
		
		
		//-------------

		//Bottom_Center
		
		HBox hb_Bottom_Center=new HBox();
		GridPane gp_bottom_Center=new GridPane();
		
		Text t_start=new Text("Start Point: ");
		ComboBox<String> cb_start= new ComboBox<>();
		cb_start.setDisable(true);
		cb_start.setStyle("-fx-Border-color: null ;-fx-Border-width:3;");
		
		Text t_end=new Text("End Point: ");
		ComboBox<String> cb_end= new ComboBox<>();
		cb_end.setDisable(true);
		cb_end.setStyle("-fx-Border-color: null ;-fx-Border-width:3;");
		
		gp_bottom_Center.setVgap(20);
		gp_bottom_Center.setHgap(5);
		gp_bottom_Center.add(t_start, 0, 0);
		gp_bottom_Center.add(cb_start, 1, 0);
		
		gp_bottom_Center.add(t_end, 4, 0);
		gp_bottom_Center.add(cb_end, 5, 0);
		
		
		hb_Bottom_Center.getChildren().add(gp_bottom_Center);
		hb_Bottom_Center.setPadding(new Insets(13, 0, 0, 427));
		
		//==================================
		
		
		vb_center.getChildren().addAll(hb_topTop_Center,hb_center_Center);
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
			// action of load button
			
			b_loadfile.setOnAction(e->{
					// init value
					filePath="";
					fileData = "";
					numOfCities = 0;
					start = "";
					end = "";
					cbEnd_selectedItem="";
					t_successDataLoad.setText("");
					
					city_ol_start= FXCollections.observableArrayList();
					city_ol_end= FXCollections.observableArrayList();
					city_dataList= FXCollections.observableArrayList();
					city_ol_start.clear();
					city_ol_end.clear();
					city_dataList.clear();
					cb_start.setDisable(true);
					cb_start.getSelectionModel().clearSelection();
					cb_end.setDisable(true);
					cb_end.getSelectionModel().clearSelection();
	               	
					
					
					try {

						// to select a file.
						FileChooser fileChooser = new FileChooser();
						File selectedFile = fileChooser.showOpenDialog(stage_fx);
						
						
						// to show a path of a file.
						if (!selectedFile.getAbsolutePath().equals(null)) {
							filePath = selectedFile.getAbsolutePath();
							l1.setText(" " + filePath);
						}
						
						// if path is empty
		                 if(l1.getText().equals("")) {
		                	 System.out.println("path is empty");
		                 }
		                 else {
		                	 if(vb_Top.getChildren().contains(hb_invalidChooseFile)) {
		                		 vb_Top.getChildren().remove(hb_invalidChooseFile);
		                	 }
		                	 
		                	 l1.setStyle("-fx-background-color: white;-fx-Border-color: #48D1CC;-fx-Border-width:5;"
		             				+ "-fx-font-size:15;-fx-font-weight:bold;-fx-text-fill: #FF4500");
		                 }
		                 
						// get data thats inside the file.
						selectedFile = new File(filePath);
						Scanner in = new Scanner(selectedFile);

						String s[] = null;
						String s_insideLine[] = null;
						//////////////////////////////////
						// i, s for the line
						// j, s_insideLine for index on the line
		                
						int curr_stage=0;
						int size=0;
						//////////////////////////////////
						// curr_stage for the current stage
						// size for the next stage
						
						for (int i = 0; in.hasNextLine(); i++) {
							String line = in.nextLine();
							// first line
							if (i == 0) {
								numOfCities = Integer.parseInt(line);
								arr_city = new City[numOfCities][numOfCities];

								// print
								fileData += "Num Of Cities: " + numOfCities + "\n\n";
							}

							// second line
							else if (i == 1) {
								s = line.split(",");
								start = s[0].trim();
								end = s[1].trim();

								// print
								fileData += "Start Point : " + start + "\n" + "Finish Point: " + end + "\n"
										+ "----------------------------------------------------------------------------------------"
										+ "\n";
							}

							// rest line
						else {
							line = line.replaceAll("\\[|\\]", " ");
							s = line.split("  ");
							
							if (s.length > 0) {
								for (int j = 0; j < s.length; j++) {
									s_insideLine = s[j].split(",");

									arr_city[i - 2][j] = new City(null, 0, 0, 0);

										
										if (j == 0) {
											
											arr_city[i - 2][j].setCity_name(s_insideLine[0].trim());
											arr_city[i - 2][j].setStage(curr_stage);
											city_dataList.add(arr_city[i - 2][j]);
											
										}
										else {
											
											arr_city[i - 2][j].setCity_name(s_insideLine[0].trim());
											arr_city[i - 2][j].setPetrol_cost(Integer.parseInt(s_insideLine[1].trim()));
											arr_city[i - 2][j].setHotel_cost(Integer.parseInt(s_insideLine[2].trim()));
											
											
											arr_city[i - 2][j].setStage(arr_city[i-2][0].getStage()+1);
											
											city_dataList.add(arr_city[i - 2][j]);
											
										}								
										
										
										if ((i - 2) == size) {
											size += s.length-1;
											curr_stage++;
										}

										
										/////////////////////////////////////////////////////////////////////////////////////////
										
																				
										
										// print
										if (arr_city[i - 2][j].getHotel_cost() == 0
												&& arr_city[i - 2][j].getPetrol_cost() == 0) {

											if (arr_city[i - 2][j].getCity_name().length() > 4) {
												fileData += "\t\t\t\t\t\t\t\t\t" + "     " + "("
														+ arr_city[i - 2][j].getCity_name() + ")" + "\n"
														+ "\t\t\t\t\t\t\t\t\t" + "        " + " |" + "\n" + "\t\t\t\t\t\t\t\t\t"
														+ "        " + " |" + "\n" + "\t\t\t\t\t\t\t\t\t" + "        " + "V"
														+ "\n";
											} else {
												fileData += "\t\t\t\t\t\t\t\t\t" + "        " + "("
														+ arr_city[i - 2][j].getCity_name() + ")" + "\n"
														+ "\t\t\t\t\t\t\t\t\t" + "          " + "|" + "\n" + "\t\t\t\t\t\t\t\t\t"
														+ "          " + "|" + "\n" + "\t\t\t\t\t\t\t\t\t" + "         " + "V"
														+ "\n";
											}
											// for combobox
											city_ol_start.add(arr_city[i-2][j].getCity_name());
											city_ol_end.add(arr_city[i-2][j].getCity_name());
										
										} else {
											fileData += "\t\t\t\t\t       " + arr_city[i - 2][j].toString() + "\n";

										}
										fileData += "\t\t\t\t\t     "
												+ "--------------------------------------------------------------------" + "\n";
									}
							
					           
									
									System.out.println("\n");
								}
							

							}
		               		
		               	 }
		               
						//l_stage.printData();
						
						// set combobox data
		               	if(!city_ol_start.isEmpty()) {
		               		
		               		// by default value
		               		///////////////////////////////////////////////////////
		               		
		               		
		               		// start and end points, removed
		               		city_ol_start.remove(end);
		               		city_ol_end.remove(start);
		               		//
		               		
		               		// start and end points, exists
//		               		city_ol_start.add(end);
//		               		city_ol_end.add(start);
		               		//
		               		
		               		//////////////////////////////////////////////////////
		               		
		               		//city_ol_end.add(end);
		               		cb_start.setItems(city_ol_start);
		                   	cb_end.setItems(city_ol_end);
		                   	
		                   	cb_start.setDisable(false);
		                   	
		                   	
		                   	
		               		
		               	
		                   	
		                   	
		                   	
		                   	
		               	}
		               	
		               	ta2.setText(fileData);
		               	
		               	t_successDataLoad.setStyle("-fx-font-weight:bold;-fx-font-size:13;");
		               	t_successDataLoad.setFill(Color.BLACK);
		               	t_successDataLoad.setText("-- All Data Loaded Successfully (100%)");
		               	
		               	
		               	if(!ta2.getText().equals("")) {
		               		ta2.setStyle("-fx-Border-color: #000000;-fx-Border-width:1;");
		               	}
		         
		               	
		               	

		               	in.close();
		        	}
					catch (FileNotFoundException f_ex) {

		        		if( (!vb_Top.getChildren().contains(hb_invalidChooseFile)) && (filePath.equals(""))) {
		        			t_err_invalidChooseFile.setText(" * Please Choose a Valid File !!!");
		               	 	l1.setStyle("-fx-background-color: white;-fx-Border-color: #FF0000;-fx-Border-width:5;"
		             				+ "-fx-font-size:15;-fx-font-weight:bold;-fx-text-fill: #FF4500");
		               	 	
		               	 	vb_Top.getChildren().add(hb_invalidChooseFile);
		        		}
		        		System.out.println("error");
		        		
		        	}
			});
					
			
			
			
			// Bottom ******************************************************
			// Contains hb_bottom
			/*
			 * hb_bottom : Button(b_seeRes)
			*/
			
			//==================================
			HBox hb_bottom=new HBox(); 
			Button b_seeRes=new Button("Calculate");
			b_seeRes.setDisable(true);
			b_seeRes.setPrefSize(120, 43);
			b_seeRes.setStyle("-fx-background-color: white; -fx-background-radius: 13; -fx-Border-color: #C0C0C0;"
	    			+ "-fx-Border-radius: 10;-fx-font-size:15;-fx-Border-width:4;-fx-font-weight:bold; -fx-text-fill: #C0C0C0");
		
			
			
           	
			hb_bottom.getChildren().add(b_seeRes);
			hb_bottom.setPadding(new Insets(-70, 0, 15, 1320));
			
			b_loadfile.setOnAction(e->{
				// init value
				filePath="";
				fileData = "";
				numOfCities = 0;
				start = "";
				end = "";
				cbEnd_selectedItem="";
				t_successDataLoad.setText("");
				
				city_ol_start= FXCollections.observableArrayList();
				city_ol_end= FXCollections.observableArrayList();
				city_dataList= FXCollections.observableArrayList();
				city_ol_start.clear();
				city_ol_end.clear();
				city_dataList.clear();
				cb_start.setDisable(true);
				cb_start.getSelectionModel().clearSelection();
				cb_end.setDisable(true);
				cb_end.getSelectionModel().clearSelection();
               	
				
				
				try {

					// to select a file.
					FileChooser fileChooser = new FileChooser();
					File selectedFile = fileChooser.showOpenDialog(stage_fx);
					
					
					// to show a path of a file.
					if (!selectedFile.getAbsolutePath().equals(null)) {
						filePath = selectedFile.getAbsolutePath();
						l1.setText(" " + filePath);
					}
					
					// if path is empty
	                 if(l1.getText().equals("")) {
	                	 System.out.println("path is empty");
	                 }
	                 else {
	                	 if(vb_Top.getChildren().contains(hb_invalidChooseFile)) {
	                		 vb_Top.getChildren().remove(hb_invalidChooseFile);
	                	 }
	                	 
	                	 l1.setStyle("-fx-background-color: white;-fx-Border-color: #48D1CC;-fx-Border-width:5;"
	             				+ "-fx-font-size:15;-fx-font-weight:bold;-fx-text-fill: #FF4500");
	                 }
	                 
					// get data thats inside the file.
					selectedFile = new File(filePath);
					Scanner in = new Scanner(selectedFile);

					String s[] = null;
					String s_insideLine[] = null;
					//////////////////////////////////
					// i, s for the line
					// j, s_insideLine for index on the line
	                
					int curr_stage=0;
					int size=0;
					//////////////////////////////////
					// curr_stage for the current stage
					// size for the next stage
					
					for (int i = 0; in.hasNextLine(); i++) {
						String line = in.nextLine();
						// first line
						if (i == 0) {
							numOfCities = Integer.parseInt(line);
							arr_city = new City[numOfCities][numOfCities];

							// print
							fileData += "Num Of Cities: " + numOfCities + "\n\n";
						}

						// second line
						else if (i == 1) {
							s = line.split(",");
							start = s[0].trim();
							end = s[1].trim();

							// print
							fileData += "Start Point : " + start + "\n" + "Finish Point: " + end + "\n"
									+ "----------------------------------------------------------------------------------------"
									+ "\n";
						}

						// rest line
					else {
						line = line.replaceAll("\\[|\\]", " ");
						s = line.split("  ");
						
						if (s.length > 0) {
							for (int j = 0; j < s.length; j++) {
								s_insideLine = s[j].split(",");

								arr_city[i - 2][j] = new City(null, 0, 0, 0);

									
									if (j == 0) {
										
										arr_city[i - 2][j].setCity_name(s_insideLine[0].trim());
										arr_city[i - 2][j].setStage(curr_stage);
										city_dataList.add(arr_city[i - 2][j]);
										
									}
									else {
										
										arr_city[i - 2][j].setCity_name(s_insideLine[0].trim());
										arr_city[i - 2][j].setPetrol_cost(Integer.parseInt(s_insideLine[1].trim()));
										arr_city[i - 2][j].setHotel_cost(Integer.parseInt(s_insideLine[2].trim()));
										
										
										arr_city[i - 2][j].setStage(arr_city[i-2][0].getStage()+1);
										
										city_dataList.add(arr_city[i - 2][j]);
										
									}								
									
									
									if ((i - 2) == size) {
										size += s.length-1;
										curr_stage++;
									}

									
									/////////////////////////////////////////////////////////////////////////////////////////
									
																			
									
									// print
									if (arr_city[i - 2][j].getHotel_cost() == 0
											&& arr_city[i - 2][j].getPetrol_cost() == 0) {

										if (arr_city[i - 2][j].getCity_name().length() > 4) {
											fileData += "\t\t\t\t\t\t\t\t\t" + "     " + "("
													+ arr_city[i - 2][j].getCity_name() + ")" + "\n"
													+ "\t\t\t\t\t\t\t\t\t" + "        " + " |" + "\n" + "\t\t\t\t\t\t\t\t\t"
													+ "        " + " |" + "\n" + "\t\t\t\t\t\t\t\t\t" + "        " + "V"
													+ "\n";
										} else {
											fileData += "\t\t\t\t\t\t\t\t\t" + "        " + "("
													+ arr_city[i - 2][j].getCity_name() + ")" + "\n"
													+ "\t\t\t\t\t\t\t\t\t" + "          " + "|" + "\n" + "\t\t\t\t\t\t\t\t\t"
													+ "          " + "|" + "\n" + "\t\t\t\t\t\t\t\t\t" + "         " + "V"
													+ "\n";
										}
										// for combobox
										city_ol_start.add(arr_city[i-2][j].getCity_name());
										city_ol_end.add(arr_city[i-2][j].getCity_name());
									
									} else {
										fileData += "\t\t\t\t\t       " + arr_city[i - 2][j].toString() + "\n";

									}
									fileData += "\t\t\t\t\t     "
											+ "-----------------------------------------------------------------" + "\n";
								}
						
				           
								
								System.out.println("\n");
							}
						

						}
	               		
	               	 }
	               
					//l_stage.printData();
					
					// set combobox data
	               	if(!city_ol_start.isEmpty()) {
	               		
	               		// by default value
	               		///////////////////////////////////////////////////////
	               		
	               		
	               		// start and end points, removed
	               		city_ol_start.remove(end);
	               		city_ol_end.remove(start);
	               		//
	               		
	               		// start and end points, exists
//	               		city_ol_start.add(end);
//	               		city_ol_end.add(start);
	               		//
	               		
	               		//////////////////////////////////////////////////////
	               		
	               		//city_ol_end.add(end);
	               		cb_start.setItems(city_ol_start);
	                   	cb_end.setItems(city_ol_end);
	                   	
	                   	cb_start.setDisable(false);
	                   	
	                   	
	                   	
	               		
	               	
	                   	
	                   	
	                   	
	                   	
	               	}
	               	
	               	ta2.setText(fileData);
	               	
	               	t_successDataLoad.setStyle("-fx-font-weight:bold;-fx-font-size:13;");
	               	t_successDataLoad.setFill(Color.BLACK);
	               	t_successDataLoad.setText("-- All Data Loaded Successfully (100%)");
	               	
	               	
	               	if(!ta2.getText().equals("")) {
	               		ta2.setStyle("-fx-Border-color: #000000;-fx-Border-width:1;");
	               	}
	         
	               	
	               	

	               	in.close();
	        	}
				catch (FileNotFoundException f_ex) {

	        		if( (!vb_Top.getChildren().contains(hb_invalidChooseFile)) && (filePath.equals(""))) {
	        			t_err_invalidChooseFile.setText(" * Please Choose a Valid File !!!");
	               	 	l1.setStyle("-fx-background-color: white;-fx-Border-color: #FF0000;-fx-Border-width:5;"
	             				+ "-fx-font-size:15;-fx-font-weight:bold;-fx-text-fill: #FF4500");
	               	 	
	               	 	vb_Top.getChildren().add(hb_invalidChooseFile);
	        		}
	        		System.out.println("error");
	        		
	        	}
				b_seeRes.setDisable(false);
		});
			
			// for button
	    	b_seeRes.setStyle("-fx-background-color: #48D1CC; -fx-background-radius: 10; -fx-Border-color: 	#48D1CC;"
        			+ "-fx-Border-radius: 10;-fx-font-size:15;-fx-Border-width:4;-fx-font-weight:bold; -fx-text-fill: white");
	    	// enter
			b_seeRes.addEventHandler(MouseEvent.MOUSE_ENTERED,
			        new EventHandler<MouseEvent>() {
			          @Override
			          public void handle(MouseEvent e) {
			            b_seeRes.setEffect(shadow);
			            b_seeRes.setStyle("-fx-background-color: white; -fx-background-radius: 13; -fx-Border-color: #48D1CC;"
			        			+ "-fx-Border-radius: 10;-fx-font-size:15;-fx-Border-width:4;-fx-font-weight:bold; -fx-text-fill: #48D1CC");
			          }
			        });
			// exit
			b_seeRes.addEventHandler(MouseEvent.MOUSE_EXITED,
			        new EventHandler<MouseEvent>() {
			          @Override
			          public void handle(MouseEvent e) {
			            b_seeRes.setEffect(null);
			            b_seeRes.setStyle("-fx-background-color: #48D1CC; -fx-background-radius: 10; -fx-Border-color: 	#48D1CC;"
			        			+ "-fx-Border-radius: 10;-fx-font-size:15;-fx-Border-width:4;-fx-font-weight:bold; -fx-text-fill: white");
			          }
			        });
			
			// action
			b_seeRes.setOnAction(e->{
				scene2_solutionP=null;
				scene2_solutionP = new Scene(SolutionsPage(),1700,800);
				stage_fx.setScene(scene2_solutionP);
			});
			////////////////////////////////////////////////////////////////
			
			bp_mainPage.setTop(vb_Top);
			bp_mainPage.setCenter(vb_center);
			bp_mainPage.setBottom(hb_bottom);
			
			bp_mainPage.setStyle("-fx-background-image: url('" + getClass().getResource("/D3_Graphic_images/A1.png").toExternalForm() + "'); -fx-background-size: cover, auto; -fx-decorator-color: blue;");
			return bp_mainPage;

	}

	
	// Solution Page
	public BorderPane SolutionsPage() {
		BorderPane bp_sol_p = new BorderPane();
		// Top *************************************************************************************************************
		VBox vb = new VBox();

        vb.getChildren().add(dpTable);

        // Set up TextArea for result
        resultTextArea.setEditable(false);
        vb.getChildren().add(resultTextArea);
		
        file = new File(filePath);
        
        loadData(file);
        
        
		// Bottom *************************************************************************************************************
		HBox hb_bottom=new HBox();
		Button b_back=new Button("Back");
		b_back.setPrefSize(120, 43);
		b_back.setStyle("-fx-background-color: #48D1CC; -fx-background-radius: 10; -fx-Border-color: #48D1CC;"
    			+ "-fx-Border-radius: 10;-fx-font-size:15;-fx-Border-width:4;-fx-font-weight:bold; -fx-text-fill: white");
        
        b_back.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            b_back.setEffect(shadow);
		            b_back.setStyle("-fx-background-color: #FF4500; -fx-background-radius: 10; -fx-Border-color: 	#48D1CC;"
		        			+ "-fx-Border-radius: 10;-fx-font-size:15;-fx-Border-width:4;-fx-font-weight:bold; -fx-text-fill: #48D1CC");
		          }
		        });
		b_back.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            b_back.setEffect(null);
		            b_back.setStyle("-fx-background-color: #48D1CC; -fx-background-radius: 10; -fx-Border-color: #48D1CC;"
		        			+ "-fx-Border-radius: 10;-fx-font-size:15;-fx-Border-width:4;-fx-font-weight:bold; -fx-text-fill: white");
		          }
		        });
		
		b_back.setOnAction(e->{
			stage_fx.setScene(scene1_mainP);
		});
		hb_bottom.getChildren().add(b_back);
		hb_bottom.setPadding(new Insets(-70, 0, 15, 1320));
		
		
		
		
		////////////////////////////////////////////////////////////////
		
		bp_sol_p.setTop(vb);
		bp_sol_p.setBottom(hb_bottom);
		
		bp_sol_p.setStyle("-fx-background-image: url('" + getClass().getResource("/D3_Graphic_images/A1.png").toExternalForm() + "'); -fx-background-size: cover, auto; -fx-decorator-color: blue;");
		return bp_sol_p;
	}

	
	
	//=== Method ------------------------------------------------------	
	
	private void loadData(File file) {
        File data = file;
        try {
            Scanner in = new Scanner(data);

            // Reading the number of cities from the first line of the file
            number_of_cities = Integer.parseInt(in.nextLine());//14

            // Reading the start and end cities from the second line of the file
            String[] start_End = in.nextLine().split(",");
            String City_Start = start_End[0].trim();//start
            String City_End = start_End[1].trim();//End

            int n = 0; 
            city = new String[number_of_cities]; // Array to store city names
            int[] stage = new int[number_of_cities]; // Array to store stage numbers for cities

            stage[0] = 0; // Setting the stage of the first city to 0

            int currentstage = 0; 
            int nextStage = 0;

            // Creating a HashMap to store city names as keys and their nearby cities as values
            Map<String, ArrayList<City>> lists = new HashMap<>();

            // Loop to read city data from the file
            for (int i = 0; i < number_of_cities - 1; i++) {
                String[] line = in.nextLine().split(", "); // Splitting the line to get city data
                //Start [A,22,70] [B,8,80] [C,12,80]
                String cityName = line[0]; 
                ArrayList<City> Nearby_cities = new ArrayList<>(); // List to store nearby cities

                city[i] = cityName; // Storing the city name in the array

                if (i == currentstage) { // Checking if the current city belongs to a new stage
                	nextStage++; // Incrementing the stage counter
                    for (int count = 1; count < line.length; count++) {
                    	//System.out.println(line.length);
                    	//System.out.println(line[count]);
                        stage[++n] = nextStage; // Setting the stage for the next city
                        //System.out.println(stage[n]);
                        currentstage++; // Incrementing the stage counter
                    }
                }

                // Loop to read nearby cities and their costs
                for (int j = 1; j < line.length; j++) {
                	//System.out.println(line[j]);
                    String[] cityInfo = line[j].substring(1, line[j].length() - 1).split(",");
                    String Nearby_Cities = cityInfo[0].trim();
                    //System.out.println(Nearby_Cities = cityInfo[0].trim());
                    int petrol_Cost = Integer.parseInt(cityInfo[1].trim());
                    int hotel_Cost = Integer.parseInt(cityInfo[2].trim());
                    Nearby_cities.add(new City(Nearby_Cities, petrol_Cost, hotel_Cost,0));
                }

                lists.put(cityName, Nearby_cities); // Storing the city and its nearby cities in the HashMap
            }

            city[n] = City_End; // Setting the last city as the end city/end
            stage[n] = nextStage; // Setting the stage for the end city//5

            dp = new int[number_of_cities][number_of_cities]; // 2D array to store costs
            Citydp = new String[number_of_cities][number_of_cities]; // 2D array to store city paths

            

            dp[number_of_cities - 1][number_of_cities - 1] = 0; // Setting the cost of reaching the end city

            for (int i = number_of_cities - 2; i >= 0; i--) {
            	ArrayList<City> LI = lists.get(city[i]); 
            	//System.out.println(LI);

                for (int j = i + 1; j < number_of_cities; j++) {
                    if (stage[i] + 1 == stage[number_of_cities - 1]) { 
                    	

                        if (stage[i] != stage[j]) {
                            dp[i][j] = LI.get(0).getHotel_cost() + LI.get(0).getPetrol_cost();
                        }
                    } 
                    else {
                        if (stage[i] + 1 == stage[j]) { // for prev_stage and stage
                            for (int c = 0; c < LI.size(); c++) {
                                City city1 = LI.get(c);
                                dp[i][j] = city1.getHotel_cost() + city1.getPetrol_cost();
                                j++;
                            }
                            j--; 
                        } else { // Formula
                            for (int k = j; stage[k] > stage[i]; k--) {
                                if (stage[k] != stage[j] && stage[k] + 1 == stage[j]) {
                                    if (dp[i][j] > 0) {
                                        if (dp[i][j] > dp[i][k] + dp[k][j]) {
                                            Citydp[i][j] = city[k];
                                        } else if (dp[i][j] == dp[i][k] + dp[k][j]) {
                                            Citydp[i][j] = city[k] + "," + Citydp[i][j];
                                        }
                                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                                    } else {
                                        dp[i][j] = dp[i][k] + dp[k][j];
                                        Citydp[i][j] = city[k];
                                    }

                                }
                            }
                        }
                    }
                }
            }

            String[] s = Citydp[0][Citydp.length - 1].trim().split(",");
//            for(int i=0;i<s.length;i++) {
//            	System.out.println(s[i]);
//            }
            
            for (int h = 0; h < s.length; h++) {
                String str = s[h] + "," + City_End;
                for (int j = Citydp.length - 2; j > 0; j--) {
                    if (city[j].equals(s[h])) {
                        if (Citydp[0][j] != null) {
                            String st = str;
                            str = Citydp[0][j] + "," + st;
                            s[h] = Citydp[0][j];
                        }
                    }
                }
                String st = str;
                str = City_Start + "," + st;
                
                resultTextArea.appendText("The expected result:  " + str + " Cost:" + dp[0][number_of_cities - 1] + "\n\n");
            }
            
            for(int i=0;i<Citydp.length;i++) {
            	for(int j=0;j<Citydp.length;j++) {
            		if(Citydp[i][j]==null) {
            			Citydp[i][j]="";
            		}
            	}
            }

            for (int i = 0; i < number_of_cities; i++) {
                String[] row = new String[number_of_cities];

                row[0] = city[i];

                for (int j = 1; j < number_of_cities; j++) {
                    row[j] = String.valueOf(dp[i][j])+" "+Citydp[i][j];
                }
                dpTable.getItems().add(row);
            }

            for (int i = 0; i < number_of_cities; i++) {
                TableColumn<String[], String> column = new TableColumn<>(city[i]);
                final int columnIndex = i;
                column.setCellValueFactory(cellDataFeatures -> new SimpleStringProperty(cellDataFeatures.getValue()[columnIndex]));
                dpTable.getColumns().add(column);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	
	
	
	//=================================================================
	
	public static void main(String[] args) {
		launch(args);
	}
}
