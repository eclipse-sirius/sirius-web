/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { makeStyles } from '@material-ui/core/styles';
import Tab from '@material-ui/core/Tab';
import Tabs from '@material-ui/core/Tabs';
import CloseIcon from '@material-ui/icons/Close';
import React from 'react';
import { RepresentationNavigationProps } from 'workbench/RepresentationNavigation.types';
import { Representation, Selection } from 'workbench/Workbench.types';

const useRepresentationNavigationStyles = makeStyles((theme) => ({
  tabsRoot: {
    minHeight: theme.spacing(4),
    borderBottomColor: theme.palette.divider,
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
  },
  tabRoot: {
    minHeight: theme.spacing(4),
    textTransform: 'none',
  },
  tabLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    '& > *:nth-child(2)': {
      marginLeft: theme.spacing(1),
    },
  },
}));

const a11yProps = (id: string) => {
  return {
    id: `simple-tab-${id}`,
    'aria-controls': `simple-tabpanel-${id}`,
  };
};

export const RepresentationNavigation = ({
  representations,
  displayedRepresentation,
  onRepresentationClick,
  onClose,
}: RepresentationNavigationProps) => {
  const classes = useRepresentationNavigationStyles();

  const onChange = (_, value) => {
    const representationSelected = representations.find((representation) => representation.id === value);
    const selection: Selection = {
      id: representationSelected.id,
      label: representationSelected.label,
      kind: representationSelected.kind,
    };
    onRepresentationClick(selection);
  };
  const onRepresentationClose = (event, representation: Representation) => {
    event.stopPropagation();
    onClose(representation);
  };
  return (
    <Tabs
      classes={{ root: classes.tabsRoot }}
      value={displayedRepresentation.id}
      onChange={onChange}
      variant="scrollable"
      scrollButtons="on"
      textColor="primary"
      indicatorColor="primary">
      {representations.map((representation) => {
        return (
          <Tab
            {...a11yProps(representation.id)}
            classes={{ root: classes.tabRoot }}
            value={representation.id}
            label={
              <div className={classes.tabLabel}>
                {representation.label}
                <CloseIcon fontSize="small" onClick={(event) => onRepresentationClose(event, representation)} />
              </div>
            }
            key={representation.id}
            data-testid={`representation-tab-${representation.label}`}
            data-testselected={representation.id === displayedRepresentation.id}
            data-representationid={representation.id}
          />
        );
      })}
    </Tabs>
  );
};
