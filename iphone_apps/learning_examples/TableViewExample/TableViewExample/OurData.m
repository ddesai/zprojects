//
//  OurData.m
//  TableViewExample
//
//  Created by testuser on 10/4/12.
//  Copyright (c) 2012 testuser. All rights reserved.
//

#import "OurData.h"

@implementation OurData

static OurData *sharedInstance = nil;

- (id) init
{
    return nil;
}

- (id) initPrivate
{
    if(self = [super init])
    {
        rawData = [NSMutableArray arrayWithObjects:@"Banana", @"Apple", @"Orange", @"Kiwi", @"Cherry", @"Tomato", @"Plum", @"Grapefruit",@"Watermelon", @"Mango",  @"Avocado", @"One", @"two", @"three", nil];
    }
    return self;
}

+ (OurData *) sharedData
{
    if(sharedInstance == nil)
    {
        sharedInstance = [[OurData alloc] initPrivate];
    }
    return sharedInstance;
}

- (NSArray*) data
{
    return rawData;
}

@end
