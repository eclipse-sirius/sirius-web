/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import PropTypes from 'prop-types';

import { classNames } from '../../common/classnames';

import { Button } from '../buttons/Button';
import { Body, Card, Header, Title } from '../cards/Card';
import { List, ListItem } from '../list/List';
import { TabBar } from '../tabbar/TabBar';

import './WorkflowCard.css';

const propTypes = {
  projectName: PropTypes.string.isRequired,
  pageIdentifier: PropTypes.string.isRequired,
  pages: PropTypes.array.isRequired,
  sections: PropTypes.array.isRequired,
  onTabClick: PropTypes.func,
  onActivityClick: PropTypes.func
};
const defaultProps = {
  projectName: '',
  pageIdentifier: '',
  pages: [],
  sections: [],
  onTabClick: () => {},
  onActivityClick: () => {}
};

const WORKFLOWCARD__CLASS_NAMES = 'workflowcard';

/**
 * The WorkflowCard component is used to render the workflow of the page.
 */
export const WorkflowCard = ({
  className,
  projectName,
  pageIdentifier,
  pages,
  sections,
  onTabClick,
  onActivityClick,
  ...props
}) => {
  let selectedTabIndex = 0;
  for (let index = 0; index < pages.length; index++) {
    let page = pages[index];
    if (page.identifier === pageIdentifier) {
      selectedTabIndex = index;
    }
  }

  const workflowCardClassNames = classNames(WORKFLOWCARD__CLASS_NAMES, className);
  return (
    <Card {...props} className={workflowCardClassNames}>
      <Header>
        <Title>Workflow</Title>
      </Header>
      <Body>
        <TabBar
          selectedTabIndex={selectedTabIndex}
          tabs={pages.map(page => page.name)}
          onTabClick={onTabClick}
        />
        {sections.map(section => (
          <Section
            key={section.identifier}
            projectName={projectName}
            pageIdentifier={pageIdentifier}
            section={section}
            onActivityClick={onActivityClick}
          />
        ))}
      </Body>
    </Card>
  );
};
WorkflowCard.propTypes = propTypes;
WorkflowCard.defaultProps = defaultProps;

const SECTION__CLASS_NAMES = 'section';
const SECTION_TITLE__CLASS_NAMES = 'section-title title-m';

const Section = ({
  className,
  projectName,
  pageIdentifier,
  section,
  onActivityClick,
  ...props
}) => {
  const sectionClassNames = classNames(SECTION__CLASS_NAMES, className);
  return (
    <div className={sectionClassNames} {...props}>
      <h2 className={SECTION_TITLE__CLASS_NAMES}>{section.name}</h2>
      <List>
        {section.activities.map(activity => (
          <ListItem key={activity.identifier}>
            {activity.name}
            <Button
              onClick={() =>
                onActivityClick(
                  projectName,
                  pageIdentifier,
                  section.identifier,
                  activity.identifier
                )
              }>
              Run
            </Button>
          </ListItem>
        ))}
      </List>
    </div>
  );
};
