//
//  DataManager.h
//  DataViewer
//
//  Created by testuser on 10/8/12.
//  Copyright (c) 2012 z2d2sky. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "DataModel.h"

@interface DataManager : NSObject
{
    DataModel* dataModel;
}

@property DataModel *dataModel;

- (void) populateDataModel;

@end

