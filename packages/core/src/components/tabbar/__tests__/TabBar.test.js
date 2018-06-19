/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import ReactDOM from 'react-dom';
import ReactTestUtils from 'react-dom/test-utils';
import Renderer from 'react-test-renderer';

import { TabBar } from '../TabBar';

import { PRISTINE__STATE, MOVED__STATE } from '../TabBarFiniteStateMachine';

describe('TabBar', () => {
  it('renders a list of tabs', () => {
    const tabs = ['First', 'Second', 'Third'];
    const tabBarComponent = Renderer.create(<TabBar tabs={tabs} />);
    expect(tabBarComponent.toJSON()).toMatchSnapshot();

    const tabBar = tabBarComponent.getInstance();
    const { stateId } = tabBar.state;
    expect(stateId).toBe(PRISTINE__STATE);
  });

  it('renders a list of tabs with a selected tab', () => {
    const tabs = ['First', 'Second', 'Third'];
    const tabBarComponent = Renderer.create(<TabBar tabs={tabs} selectedTabIndex={1} />);
    expect(tabBarComponent.toJSON()).toMatchSnapshot();

    const tabBar = tabBarComponent.getInstance();
    const { stateId } = tabBar.state;
    expect(stateId).toBe(PRISTINE__STATE);
  });

  it('changes selected tab when clicked', () => {
    let selectedTabIndex = 0;

    const div = document.createElement('div');
    const tabs = ['First', 'Second', 'Third'];
    const tabBar = ReactDOM.render(
      <TabBar tabs={tabs} onTabClick={index => (selectedTabIndex = index)} />,
      div
    );

    expect(tabBar.state.selectedTabIndex).toBe(0);

    const tabBarDOMNode = div.childNodes[0];
    const tabsDOMNode = tabBarDOMNode.childNodes[1];
    const secondTabDOMNode = tabsDOMNode.childNodes[1];

    ReactTestUtils.Simulate.click(secondTabDOMNode);
    expect(selectedTabIndex).toEqual(1);
    expect(tabBar.state.selectedTabIndex).toBe(1);
  });

  it('changes the tab rendered when clicking on next', () => {
    const div = document.createElement('div');
    const tabs = ['First', 'Second', 'Third', 'Fourth'];
    const tabBar = ReactDOM.render(<TabBar tabs={tabs} />, div);

    const tabBarDOMNode = div.childNodes[0];
    const nextDOMNode = tabBarDOMNode.childNodes[2];

    expect(tabBar.state.stateId).toBe(PRISTINE__STATE);
    expect(tabBar.state.isPreviousAvailable).toBe(false);
    expect(tabBar.state.isNextAvailable).toBe(true);
    expect(tabBar.state.index).toBe(0);

    ReactTestUtils.Simulate.click(nextDOMNode);

    expect(tabBar.state.stateId).toBe(MOVED__STATE);
    expect(tabBar.state.isPreviousAvailable).toBe(true);
    expect(tabBar.state.isNextAvailable).toBe(false);
    expect(tabBar.state.index).toBe(1);
  });
});
