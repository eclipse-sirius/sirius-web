/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import Avatar from '@material-ui/core/Avatar';
import Tooltip from '@material-ui/core/Tooltip';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { HelpTooltip } from './HelpTooltip';
import { PropertySectionLabelProps } from './PropertySectionLabel.types';

const usePropertySectionLabelStyles = makeStyles((theme) => ({
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  subscribers: {
    marginLeft: 'auto',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    '& > *': {
      marginLeft: theme.spacing(0.5),
      marginRight: theme.spacing(0.5),
    },
  },
  avatar: {
    fontSize: '0.875rem',
    width: theme.spacing(2),
    height: theme.spacing(2),
  },
}));

export const PropertySectionLabel = ({ editingContextId, formId, widget, subscribers }: PropertySectionLabelProps) => {
  const classes = usePropertySectionLabelStyles();
  return (
    <div className={classes.propertySectionLabel}>
      <Typography variant="subtitle2">{widget.label}</Typography>
      {widget.hasHelpText ? (
        <HelpTooltip editingContextId={editingContextId} formId={formId} widgetId={widget.id} />
      ) : null}
      <div className={classes.subscribers}>
        {subscribers.map((subscriber) => (
          <Tooltip title={subscriber.username} arrow key={subscriber.username}>
            <Avatar classes={{ root: classes.avatar }}>{subscriber.username.substring(0, 1).toUpperCase()}</Avatar>
          </Tooltip>
        ))}
      </div>
    </div>
  );
};
