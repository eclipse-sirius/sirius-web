/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/
import React from 'react';

import { classNames } from '../../common/classnames';

import { Body, Card, Divider, Header, Title } from '../cards/Card';
import { LIST_WITH_HIGHLIGHT__KIND, LIST_WITH_SEPARATOR__KIND, List, ListItem } from '../list/List';

const PROJECTS_LIST_CARD__CLASS_NAMES = 'projectslistcard';

/**
 * The ProjectsListCard is used to display a list of projects as a card.
 */
export const ProjectsListCard = ({ className, ...props }) => {
  const projectsListCardClassNames = classNames(PROJECTS_LIST_CARD__CLASS_NAMES, className);
  return (
    <Card className={projectsListCardClassNames} {...props}>
      <Header>
        <Title>Projects</Title>
      </Header>
      <Divider />
      <Body>
        <List kind={[LIST_WITH_HIGHLIGHT__KIND, LIST_WITH_SEPARATOR__KIND]}>
          <ListItem key={'sirius'} to={`projects/sirius`}>
            Sirius
          </ListItem>
          <ListItem key={'acceleo'} to={`projects/acceleo`}>
            Acceleo
          </ListItem>
          <ListItem key={'m2doc'} to={`projects/m2doc`}>
            M2Doc
          </ListItem>
          <ListItem key={'emfcompare'} to={`projects/emfcompare`}>
            EMF Compare
          </ListItem>
        </List>
      </Body>
    </Card>
  );
};
