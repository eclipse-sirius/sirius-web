/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React, { Fragment } from 'react';
import PropTypes from 'prop-types';

import { classNames } from '../../common/classnames';

import { Card, Divider } from '../cards/Card';
import { IconRun } from '../icons/IconRun';
import { List, MainText, Tile, AdditionalIcon } from '../list/List';
import { LIST_WITH_HIGHLIGHT__KIND } from '../list/ListConstants';
import { SINGLE_LINE } from '../list/ListConstants';
import { LoadingConsumer } from '../loading/Loading';
import { Spacing } from '../spacing/Spacing';
import { S, M } from '../spacing/SpacingConstants';
import { TabBar } from '../tabbar/TabBar';
import { Text } from '../text/Text';
import { SEMI_BOLD, MEDIUM, LARGE } from '../text/TextConstants';

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

  let sectionsElement;
  if (pages.length > 0) {
    sectionsElement = (
      <Sections
        sections={sections}
        projectName={projectName}
        pageIdentifier={pageIdentifier}
        onActivityClick={onActivityClick}
      />
    );
  }

  const workflowCardClassNames = classNames(WORKFLOWCARD__CLASS_NAMES, className);
  return (
    <Card {...props} className={workflowCardClassNames}>
      <Spacing top={M} right={M} bottom={M} left={M}>
        <LoadingConsumer>
          {loading => (
            <Text weight={SEMI_BOLD} size={LARGE} loading={loading} hideOverflow>
              Workflow
            </Text>
          )}
        </LoadingConsumer>
      </Spacing>
      <WorkflowTabBar pages={pages} selectedTabIndex={selectedTabIndex} onTabClick={onTabClick} />
      {sectionsElement}
    </Card>
  );
};
WorkflowCard.propTypes = propTypes;
WorkflowCard.defaultProps = defaultProps;

const WorkflowTabBar = ({ pages, selectedTabIndex, onTabClick }) => {
  if (pages.length === 0) {
    return (
      <Spacing top={S} right={M} bottom={M} left={M}>
        <LoadingConsumer>
          {loading => <Text loading={loading}>No workflow pages found</Text>}
        </LoadingConsumer>
      </Spacing>
    );
  }
  return (
    <TabBar
      selectedTabIndex={selectedTabIndex}
      tabs={pages.map(page => page.name)}
      onTabClick={onTabClick}
    />
  );
};

const Sections = ({ sections, projectName, pageIdentifier, onActivityClick }) => {
  if (sections.length === 0) {
    return <EmptySections />;
  }
  return sections.map((section, index) => (
    <Fragment key={section.identifier}>
      <Section
        key={section.identifier}
        projectName={projectName}
        pageIdentifier={pageIdentifier}
        section={section}
        onActivityClick={onActivityClick}
      />
      {index + 1 < sections.length ? <Divider /> : null}
    </Fragment>
  ));
};

const EmptySections = () => (
  <Spacing top={S} right={M} bottom={M} left={M}>
    <Text>No sections found in the workflow.</Text>
  </Spacing>
);

const SECTION__CLASS_NAMES = 'section';

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
      <Spacing top={M} right={M} bottom={M} left={M}>
        <Text weight={SEMI_BOLD} size={MEDIUM} hideOverflow>
          {section.name}
        </Text>
      </Spacing>
      <Activities
        activities={section.activities}
        onActivityClick={onActivityClick}
        projectName={projectName}
        pageIdentifier={pageIdentifier}
        sectionIdentifier={section.identifier}
      />
    </div>
  );
};

const Activities = ({
  activities,
  onActivityClick,
  projectName,
  pageIdentifier,
  sectionIdentifier
}) => {
  if (activities.length === 0) {
    return <EmptyActivities />;
  }
  return (
    <ActivitiesList
      activities={activities}
      onActivityClick={onActivityClick}
      projectName={projectName}
      pageIdentifier={pageIdentifier}
      sectionIdentifier={sectionIdentifier}
    />
  );
};

const ActivitiesList = ({
  activities,
  onActivityClick,
  projectName,
  pageIdentifier,
  sectionIdentifier
}) => (
  <List kind={LIST_WITH_HIGHLIGHT__KIND}>
    {activities.map(activity => (
      <Tile kind={SINGLE_LINE} key={activity.identifier}>
        <MainText>{activity.name}</MainText>
        <AdditionalIcon>
          <IconRun
            onClick={() =>
              onActivityClick(projectName, pageIdentifier, sectionIdentifier, activity.identifier)
            }
          />
        </AdditionalIcon>
      </Tile>
    ))}
  </List>
);

const EmptyActivities = () => (
  <Spacing top={S} right={M} bottom={M} left={M}>
    <Text>No activities in the section.</Text>
  </Spacing>
);
