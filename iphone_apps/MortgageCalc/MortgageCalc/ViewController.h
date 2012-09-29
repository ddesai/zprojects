//
//  ViewController.h
//  MortgageCalc
//
//  Created by Darshan Desai - darshanbdesai@gmail.com on 9/29/12.
//  Copyright (c) 2012 darshanbdesai@gmail.com. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController

@property (strong) IBOutlet UILabel *mortgageLabel;
@property (strong) IBOutlet UITextField *borrowedTextField;
@property (strong) IBOutlet UISwitch *taxONSwitch;
@property (strong) IBOutlet UISlider *interestRateSlider;
@property (strong) IBOutlet UILabel *interestRateLabel;
@property (strong) IBOutlet UISegmentedControl *loanTermControl;

- (IBAction) calcButtonPressed:(id)sender;
- (IBAction) dismissKeyboard:(id)sender;
- (IBAction) interestRateSliderValueChanged:(id)sender;

@end
