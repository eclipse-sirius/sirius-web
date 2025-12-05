/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import CloseIcon from '@mui/icons-material/Close';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import Tooltip from '@mui/material/Tooltip';
import { ForwardedRef, forwardRef, useImperativeHandle } from 'react';
import { makeStyles } from 'tss-react/mui';
import { IconOverlay } from '../icon/IconOverlay';
import { RepresentationNavigationProps } from './RepresentationNavigation.types';
import { RepresentationMetadata, RepresentationNavigationHandle } from './Workbench.types';

const useRepresentationNavigationStyles = makeStyles()((theme) => ({
  tabsRoot: {
    minHeight: theme.spacing(3),
    borderBottomColor: theme.palette.divider,
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
  },
  tabRoot: {
    minHeight: theme.spacing(3),
    textTransform: 'none',
    padding: '6px',
  },
  tabLabel: {
    display: 'grid',
    gridTemplateColumns: 'auto 1fr auto',
    alignItems: 'center',
    width: 'inherit',
  },
  tabRepresentationIcon: {
    marginRight: theme.spacing(1),
  },
  tabLabelText: {
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    overflow: 'hidden',
  },
  tabCloseIcon: {
    marginLeft: theme.spacing(1),
    fontSize: '0.875rem',
  },
}));

const a11yProps = (id: string) => {
  return {
    id: `simple-tab-${id}`,
    'aria-controls': `simple-tabpanel-${id}`,
  };
};

export const RepresentationNavigation = forwardRef<
  RepresentationNavigationHandle | null,
  RepresentationNavigationProps
>(
  (
    { representations, displayedRepresentation, onRepresentationClick, onClose }: RepresentationNavigationProps,
    refRepresentationNavigationHandle: ForwardedRef<RepresentationNavigationHandle | null>
  ) => {
    const { classes } = useRepresentationNavigationStyles();

    const onChange = (_event: React.ChangeEvent<{}>, value: string) => {
      const representationSelected = representations.find((representation) => representation.id === value);
      if (representationSelected) {
        const { id, label, kind, iconURLs, description } = representationSelected;

        const representation: RepresentationMetadata = {
          id,
          label,
          kind,
          iconURLs,
          description,
        };
        onRepresentationClick(representation);
      }
    };

    const onRepresentationClose = (event: React.MouseEvent<SVGSVGElement>, representation: RepresentationMetadata) => {
      event.stopPropagation();
      onClose(representation);
    };

    useImperativeHandle(
      refRepresentationNavigationHandle,
      () => {
        return {
          getMainPanelConfiguration: () => {
            return {
              id: 'main',
              representationEditors: representations.map((representation) => ({
                representationId: representation.id,
                isActive: representation.id === displayedRepresentation.id,
              })),
            };
          },
        };
      },
      [representations, displayedRepresentation]
    );

    return (
      <Tabs
        classes={{ root: classes.tabsRoot }}
        value={displayedRepresentation.id}
        onChange={onChange}
        variant="scrollable"
        scrollButtons
        textColor="primary"
        indicatorColor="primary">
        {representations.map((representation) => {
          return (
            <Tab
              {...a11yProps(representation.id)}
              classes={{ root: classes.tabRoot }}
              value={representation.id}
              label={
                <Tooltip title={representation.label}>
                  <div className={classes.tabLabel}>
                    {representation.iconURLs && (
                      <div className={classes.tabRepresentationIcon}>
                        <IconOverlay
                          customIconWidth={12}
                          customIconHeight={12}
                          iconURLs={representation.iconURLs}
                          alt="representation icon"
                        />
                      </div>
                    )}
                    <div className={classes.tabLabelText}>{representation.label}</div>
                    <CloseIcon
                      className={classes.tabCloseIcon}
                      onClick={(event) => onRepresentationClose(event, representation)}
                      data-testid={`close-representation-tab-${representation.label}`}
                    />
                  </div>
                </Tooltip>
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
  }
);
