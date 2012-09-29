//
//  BlueViewController.m
//  MultiView
//
//  Created by testuser on 9/27/12.
//  Copyright (c) 2012 testuser. All rights reserved.
//

#import "BlueViewController.h"
#import "RedViewController.h"

@interface BlueViewController ()

@end

@implementation BlueViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (IBAction) showRed :(id)sender
{
    UIStoryboard *storyboard = self.storyboard;
    RedViewController *rvc = [storyboard instantiateViewControllerWithIdentifier:@"REDVC"];
    [self presentViewController:rvc animated:YES completion:nil];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
