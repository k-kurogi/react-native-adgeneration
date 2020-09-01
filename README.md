# react-native-adgeneration
![](https://badge.fury.io/js/react-native-adgeneration.svg)

## Installation
## Android
```
$ npm install react-native-adgeneration --save
$ react-native link react-native-adgeneration
```

## iOS
1. setup CocoaPods
```
$ cd ios/
$ pod init
```

2. add React dependencies in your Podfile

`Podfile`
```
target 'Your target' do

  # for ReactNative
  pod 'React', :path => '../node_modules/react-native', :subspecs => [
    'Core',
    'CxxBridge',
    'DevSupport',
    'RCTText',
    'RCTNetwork',
    'RCTWebSocket'
  ]
  pod "yoga", :path => "../node_modules/react-native/ReactCommon/yoga"

  pod 'DoubleConversion', :podspec => '../node_modules/react-native/third-party-podspecs/DoubleConversion.podspec'
  pod 'glog', :podspec => '../node_modules/react-native/third-party-podspecs/glog.podspec'
  pod 'Folly', :podspec => '../node_modules/react-native/third-party-podspecs/Folly.podspec'

end
```

3. instllation in root dir
```
$ npm install react-native-adgeneration --save
$ react-native link react-native-adgeneration
```

## Usage
```javascript
import { AdGenerationBanner } from 'react-native-adgeneration';

<AdGenerationBanner
  locationId='your_ad_id'
  bannerType='sp' // sp|rect|tablet|large
  screenWidth={Width}
/>
```

## License
MIT
  
