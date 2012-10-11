//
//  DataModel.m
//  DataViewer
//
//  Created by testuser on 10/6/12.
//  Copyright (c) 2012 z2d2sky. All rights reserved.
//

#import "DataModel.h"

@implementation DataModel

static DataModel *sharedInstance = nil;

- (id) init
{
    return nil;
}

- (id) initPrivate
{
    if(self = [super init])
    {
        ourData = [[NSMutableArray alloc] init];
    }
    return self;
}

/* Gives the DataModel pointer */
+ (DataModel *) sharedInstance
{
    if(sharedInstance == nil)
    {
        sharedInstance = [[DataModel alloc] initPrivate];
    }
    return sharedInstance;
}

/* Gives a pointer to the data array */
- (NSMutableArray*) data
{
    return ourData;
}

/* Lets you add the Data Object in the data Array */
- (void) addData: (NSObject*) newObj
{
    [ourData addObject: newObj];
}

@end