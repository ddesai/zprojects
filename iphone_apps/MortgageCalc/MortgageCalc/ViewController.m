//
//  ViewController.m
//  MortgageCalc
//
//  Created by Darshan Desai - darshanbdesai@gmail.com on 9/29/12.
//  Copyright (c) 2012 darshanbdesai@gmail.com. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

@synthesize mortgageLabel, borrowedTextField, taxONSwitch, loanTermControl;
@synthesize interestRateSlider, interestRateLabel;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (float) calculateMortgage
{

    float P;    // borrowed amount
    float T=0;  // Tax + I 0.1% of P
    float J;    // Monthly Interest - Annual Interest / 1200
    int N;      // Number of months for the loan
    float M;    // final mortgage amount

    P = borrowedTextField.text.floatValue;

    if(taxONSwitch.on)
    {
        T = 0.1 * P / 100;
    }

    J = interestRateSlider.value / 1200;

    switch(loanTermControl.selectedSegmentIndex)
    {
        case 0: N = 15*12; break;
        case 1: N = 20*12; break;
        case 2: N = 30*12; break;
        default: N = 30*12; break;
    }
    
    M = P * (J/(1-powf(1+J,-N))) + T;
    return M;
}

- (IBAction) calcButtonPressed:(id)sender
{
    NSString *mortgage = [NSString stringWithFormat:@"%.2f", [self calculateMortgage]];
    mortgageLabel.text = mortgage;
}

- (IBAction) dismissKeyboard:(id)sender
{

    [sender resignFirstResponder];
}

- (IBAction) interestRateSliderValueChanged:(id)sender
{
     interestRateLabel.text = [NSString stringWithFormat:@"%.2f", interestRateSlider.value];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
