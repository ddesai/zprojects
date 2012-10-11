//
//  Car.m
//  DataViewer
//
//  Created by testuser on 10/8/12.
//  Copyright (c) 2012 z2d2sky. All rights reserved.
//

#import "Car.h"

@implementation Car

@synthesize make, model, year, hybrid;

- (id) init
{
    make = @"Dodge";
    model = @"Grand Caravan";
    year = 2001;
    hybrid = NO;
    return self;
}

/* Setters for the ivars of Car */
- (void) setMake: (NSString*) newMake andModel: (NSString*) newModel andYear: (int) newYear andHybrid: (bool) isHybrid
{
    make = newMake;
    model = newModel;
    year = newYear;
    hybrid = isHybrid;
}

@end
