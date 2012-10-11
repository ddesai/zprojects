//
//  OurData.h
//  TableViewExample
//
//  Created by testuser on 10/4/12.
//  Copyright (c) 2012 testuser. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface OurData : NSObject
{
    NSMutableArray *rawData;
}

+ (OurData*) sharedData;
- (NSArray*) data;

@end
