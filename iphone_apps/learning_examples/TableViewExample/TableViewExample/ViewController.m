//
//  ViewController.m
//  TableViewExample
//
//  Created by testuser on 10/4/12.
//  Copyright (c) 2012 testuser. All rights reserved.
//

#import "ViewController.h"
#import "OurData.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (NSInteger) tableView:(UITableView*) tableView numberOfRowsInSection:(NSInteger)section
{
    OurData *sharedData = [OurData sharedData];
    NSArray *strings = [sharedData data];
    return [strings count];
}

- (UITableViewCell*)tableView:(UITableView*)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *ourCellID = @"CellID";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:ourCellID];
    if(cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:ourCellID];
    OurData *sharedData = [OurData sharedData];
    NSArray *strings = [sharedData data];
    cell.textLabel.text = [strings objectAtIndex: [indexPath row]];
    }
    return cell;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
