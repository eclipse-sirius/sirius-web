/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import SummarizeIcon from '@mui/icons-material/Summarize';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Skeleton from '@mui/material/Skeleton';
import { SxProps, Theme } from '@mui/material/styles';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ImpactAnalysisDialogProps } from './ImpactAnalysisDialog.types';
import { useInvokeImpactAnalysis } from './useImpactAnalysis';
import { GQLImpactAnalysisReport } from './useImpactAnalysis.types';

const useImpactAnalysisDialogStyles = makeStyles()((theme: Theme) => ({
  title: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    paddingTop: theme.spacing(2),
  },
  titleIcon: {
    color: theme.palette.primary.main,
    fontSize: '50px',
  },
  message: {
    whiteSpace: 'pre-line',
    textAlign: 'center',
  },
  confirmButton: {
    backgroundColor: theme.palette.primary.main,
    color: 'white',
    '&:hover': {
      backgroundColor: theme.palette.primary.dark,
    },
  },
}));

export const ImpactAnalysisDialog = ({
  open,
  editingContextId,
  representationId,
  toolId,
  targetObjectId,
  variables,
  onConfirm,
  onCancel,
}: ImpactAnalysisDialogProps) => {
  const [report, setReport] = useState<GQLImpactAnalysisReport | null>(null);
  const { classes } = useImpactAnalysisDialogStyles();

  const { impactAnalysisReport, loading } = useInvokeImpactAnalysis(
    editingContextId,
    representationId,
    toolId,
    targetObjectId,
    variables
  );

  useEffect(() => {
    if (impactAnalysisReport) {
      setReport(impactAnalysisReport);
    }
  }, [impactAnalysisReport]);
  return (
    <>
      <Dialog
        open={open}
        onClose={onCancel}
        aria-labelledby="impact-analysis-dialog"
        data-testid="impact-analysis-dialog">
        <div className={classes.title}>
          <SummarizeIcon className={classes.titleIcon} />
          <DialogTitle data-testid="impact-analysis-dialog-title">Impact Analysis Report</DialogTitle>
        </div>
        <DialogContent>
          {report != null ? (
            <ReportViewer
              nbElementCreated={report.nbElementCreated}
              nbElementDeleted={report.nbElementDeleted}
              nbElementModified={report.nbElementModified}
              additionalReports={report.additionalReports}
            />
          ) : (
            <LoadingViewer />
          )}
        </DialogContent>
        <DialogActions>
          <Button
            data-testid="impact-analysis-dialog-button-cancel"
            onClick={onCancel}
            disabled={loading}
            variant="outlined">
            Cancel execution
          </Button>
          <Button
            data-testid="impact-analysis-dialog-button-ok"
            className={classes.confirmButton}
            onClick={onConfirm}
            disabled={loading}
            variant="contained"
            autoFocus>
            Confirm execution
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

const LoadingViewer = () => {
  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(2),
  });
  const skeletonTextStyle: SxProps<Theme> = (theme) => ({
    fontSize: theme.typography.body1.fontSize,
    width: '60%',
  });
  return (
    <Box>
      <List dense>
        <ListItem sx={listItemStyle}>
          <Skeleton variant="text" sx={skeletonTextStyle} />
        </ListItem>
        <ListItem sx={listItemStyle}>
          <Skeleton variant="text" sx={skeletonTextStyle} />
        </ListItem>
        <ListItem sx={listItemStyle}>
          <Skeleton variant="text" sx={skeletonTextStyle} />
        </ListItem>
      </List>
    </Box>
  );
};

const ReportViewer = ({
  nbElementCreated,
  nbElementDeleted,
  nbElementModified,
  additionalReports,
}: GQLImpactAnalysisReport) => {
  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(2),
  });

  return (
    <Box>
      <List dense>
        <ListItem key={'report.nbElementCreated'} sx={listItemStyle}>
          <ListItemText
            primary={`Number of items added: ${nbElementCreated}`}
            data-testid="impact-analysis-report-nbElementCreated"
          />
        </ListItem>
        <ListItem key={'report.nbElementDeleted'} sx={listItemStyle}>
          <ListItemText
            primary={`Number of items deleted: ${nbElementDeleted}`}
            data-testid="impact-analysis-report-nbElementDeleted"
          />
        </ListItem>
        <ListItem key={'report.nbElementModified'} sx={listItemStyle}>
          <ListItemText
            primary={`Number of items modified: ${nbElementModified}`}
            data-testid="impact-analysis-report-nbElementModified"
          />
        </ListItem>
        {additionalReports?.length > 0 &&
          additionalReports.map((additionalReport, index) => {
            return (
              <ListItem key={`report.additionalReportData.${index}`} sx={listItemStyle}>
                <ListItemText primary={additionalReport} />
              </ListItem>
            );
          })}
      </List>
    </Box>
  );
};
