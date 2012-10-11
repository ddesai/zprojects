//
//  ViewController.h
//  DataViewer
//
//  Created by testuser on 10/6/12.
//  Copyright (c) 2012 z2d2sky. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController

/* For main screen */
@property (weak) IBOutlet UILabel* make;
@property (weak) IBOutlet UILabel* model;
@property (weak) IBOutlet UILabel* year;
@property (weak) IBOutlet UISwitch* hybrid;

- (IBAction) nextButton:(id)sender;
- (IBAction) prevButton:(id)sender;
- (IBAction) switchStateChanged:(id)sender;
- (IBAction) showStatsButton :(id)sender;


/* For the Stats screen */
@property (weak) IBOutlet UILabel* total_cars;
@property (weak) IBOutlet UILabel* total_hybrid_cars;
@property (weak) IBOutlet UILabel* average_years;

- (IBAction) back :(id)sender;

@end
