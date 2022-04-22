/*******************************************************************************
 * Copyright (c) 2019, 2021, 2022 Obeo.
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
import Typography from '@material-ui/core/Typography';
import ToggleButton from '@material-ui/lab/ToggleButton';
import ToggleButtonGroup from '@material-ui/lab/ToggleButtonGroup';
import { httpOrigin } from 'common/URL';
import { GroupProps } from 'properties/Group.types';
import React, { useEffect, useState } from 'react';
import { widgetToPropertySection } from './PropertySectionOperations';

const useGroupStyles = makeStyles((theme) => ({
  group: {
    display: 'flex',
    flexDirection: 'column',
    paddingTop: theme.spacing(1),
  },
  title: {
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
  verticalSections: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
  adaptableSections: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: theme.spacing(2),
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
  button: {
    paddingTop: '1px',
    paddingBottom: '0px',
  },
  buttonIcon: {
    marginRight: theme.spacing(1),
  },
}));

export const Group = ({ editingContextId, formId, group, widgetSubscriptions, setSelection, readOnly }: GroupProps) => {
  const classes = useGroupStyles();
  const [visibleWidgetIds, setVisibleWidgetIds] = useState<string[]>([]);
  useEffect(() => {
    setVisibleWidgetIds(group.widgets.map((widget) => widget.id));
  }, [group]);

  let widgetSelector = undefined;
  if (group.displayMode === 'TOGGLEABLE_AREAS') {
    widgetSelector = (
      <ToggleButtonGroup
        value={visibleWidgetIds}
        onChange={(event, newVisibleIds) => setVisibleWidgetIds(newVisibleIds)}
      >
        {group.widgets.map((widget) => {
          return (
            <ToggleButton className={classes.button} value={widget.id} key={widget.id}>
              {widget.iconURL && (
                <img
                  className={classes.buttonIcon}
                  height="16"
                  width="16"
                  alt={widget.label}
                  src={httpOrigin + widget.iconURL}
                />
              )}
              {widget.label}
            </ToggleButton>
          );
        })}
      </ToggleButtonGroup>
    );
  }

  const renderedWidgets = group.widgets
    .filter((widget) => visibleWidgetIds.includes(widget.id))
    .map((widget) =>
      widgetToPropertySection(editingContextId, formId, widget, widgetSubscriptions, setSelection, readOnly)
    );

  return (
    <div className={classes.group}>
      {group.displayMode === 'TOGGLEABLE_AREAS' ? (
        widgetSelector
      ) : (
        <Typography variant="subtitle1" className={classes.title} gutterBottom>
          {group.label}
        </Typography>
      )}
      <div className={group.displayMode === 'LIST' ? classes.verticalSections : classes.adaptableSections}>
        {renderedWidgets}
      </div>
    </div>
  );
};
