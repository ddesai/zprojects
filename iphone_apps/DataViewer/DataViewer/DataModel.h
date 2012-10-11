//
//  DataModel.h
//  DataViewer
//
//  Created by testuser on 10/6/12.
//  Copyright (c) 2012 z2d2sky. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface DataModel : NSObject
{
    NSMutableArray *ourData;
}

+ (DataModel*) sharedInstance;
- (NSMutableArray*) data;
- (void) addData: (NSObject*) newObj;

@end