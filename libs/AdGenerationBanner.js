import React, { Component } from 'react';
import {
  requireNativeComponent,
  ViewPropTypes,
  UIManager,
  findNodeHandle,
} from 'react-native';

const RNAdGenerationBanner = requireNativeComponent('RNAdGenerationBanner');

export default class AdGenerationBanner extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return <RNAdGenerationBanner
      ref={ref => this._bannerView = ref}
      {...this.props}
      style={[this.props.style, this.state.style]}
      onMeasure={event => this.handleOnMeasure(event)}
    />;
  }

  handleOnMeasure(event) {
    const { width, height } = event.nativeEvent;
    this.setState({
      style: { width, height }
    });
    if (this.props.onMeasure) this.props.onMeasure(event);
  }

  load() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this._bannerView),
      UIManager.RNAdGenerationBanner.Commands.load,
      null,
    );
  }
}

AdGenerationBanner.propTypes = {
  ...ViewPropTypes,

  locationId: string,

  // sp|rect|large|tablet
  bannerType: string,
  
  // layout measured event
  // (width, height)
  onMeasure: func,

  // load ad
  load: func
};