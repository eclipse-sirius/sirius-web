/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import Tab from '@material-ui/core/Tab';
import Tabs from '@material-ui/core/Tabs';
import { makeStyles } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import { RepresentationNavigationProps } from './RepresentationNavigation.types';
import { RepresentationMetadata } from './Workbench.types';

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
    width: 'inherit',
  },
  tabLabelText: {
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    overflow: 'hidden',
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

  const onChange = (_event: React.ChangeEvent<{}>, value: string) => {
    const representationSelected = representations.find((representation) => representation.id === value);
    if (representationSelected) {
      const { id, label, kind } = representationSelected;
      const representation: RepresentationMetadata = {
        id,
        label,
        kind,
      };
      onRepresentationClick(representation);
    }
  };

  const onRepresentationClose = (event: React.MouseEvent<SVGSVGElement>, representation: RepresentationMetadata) => {
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
                <div className={classes.tabLabelText}>{representation.label}</div>
                <CloseIcon
                  fontSize="small"
                  onClick={(event) => onRepresentationClose(event, representation)}
                  data-testid={`close-representation-tab-${representation.label}`}
                />
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
