//
//  Car.h
//  DataViewer
//
//  Created by testuser on 10/8/12.
//  Copyright (c) 2012 z2d2sky. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Car : NSObject
{
    NSString *make;
    NSString *model;
    int year;
    bool hybrid;
}

@property NSString* make;
@property NSString* model;
@property int year;
@property bool hybrid;

- (void) setMake: (NSString*) newMake andModel: (NSString*) newModel andYear: (int) newYear andHybrid: (bool) isHybrid;

@end
