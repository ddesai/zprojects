//
//  DataManager.m
//  DataViewer
//
//  Created by testuser on 10/8/12.
//  Copyright (c) 2012 z2d2sky. All rights reserved.
//

#import "DataManager.h"
#import "Car.h"

@implementation DataManager

@synthesize dataModel;

- (id) init
{
    dataModel = [DataModel sharedInstance];
    return self;
}

/* Initializes the Data Model */
- (void) populateDataModel
{
    Car *mycar;
    
    mycar = [[Car alloc] init];
    [mycar setMake:@"Honda" andModel:@"Civic" andYear:2001 andHybrid:NO];
    [dataModel addData:mycar];
    
    mycar = [[Car alloc] init];
    [mycar setMake:@"Honda" andModel:@"Accord" andYear:2002 andHybrid:NO];
    [dataModel addData:mycar];

    mycar = [[Car alloc] init];
    [mycar setMake:@"Toyota" andModel:@"Prius" andYear:2006 andHybrid:YES];
    [dataModel addData:mycar];
    
    mycar = [[Car alloc] init];
    [mycar setMake:@"Dodge" andModel:@"Grand Caravan" andYear:2004 andHybrid:NO];
    [dataModel addData:mycar];

    mycar = [[Car alloc] init];
    [mycar setMake:@"Toyota" andModel:@"Camry" andYear:2010 andHybrid:NO];
    [dataModel addData:mycar];

    mycar = [[Car alloc] init];
    [mycar setMake:@"Toyota" andModel:@"Corola" andYear:2008 andHybrid:NO];
    [dataModel addData:mycar];

    mycar = [[Car alloc] init];
    [mycar setMake:@"Honda" andModel:@"Civic" andYear:2011 andHybrid:YES];
    [dataModel addData:mycar];
}

@end

