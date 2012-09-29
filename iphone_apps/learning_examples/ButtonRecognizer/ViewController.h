//
//  ViewController.h
//  ButtonRecognizer
//
//  Created by testuser on 9/25/12.
//  Copyright (c) 2012 testuser. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController

// we dont want the instance of this label object to disappear until this UI exists
// If you make the attribute
// "strong" - it will keep the label object until the ViewController object gets destroyed
// "weak" --
// default property is - assign

@property (strong) IBOutlet UILabel *label;
@property (strong) IBOutlet UIButton *button;
@property (strong) IBOutlet UITextField *textField;

- (IBAction) recognizeButtonPressed:(id)sender;
- (IBAction) dismissKeyboard:(id)sender;

@end
