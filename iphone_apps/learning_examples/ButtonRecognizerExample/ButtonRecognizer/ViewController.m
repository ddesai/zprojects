//
//  ViewController.m
//  ButtonRecognizer
//
//  Created by testuser on 9/25/12.
//  Copyright (c) 2012 testuser. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

@synthesize label;
@synthesize button;
@synthesize textField;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (IBAction) recognizeButtonPressed:(id)sender
{
    NSString *newString = [NSString stringWithFormat:@"Howdy, %@",textField.text];
    label.text = newString;
    button.titleLabel.text = @"dont press me";
}

- (IBAction) dismissKeyboard:(id)sender
{
    [sender resignFirstResponder];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
